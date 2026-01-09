package org.ormi.priv.tfa.orderflow.productregistry.read.application;

import java.util.Optional;

import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.read.ProductStreamElementDto;
import org.ormi.priv.tfa.orderflow.cqrs.Projector.ProjectionResult;
import org.ormi.priv.tfa.orderflow.kernel.Product;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1Envelope;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.persistence.ProductViewRepository;
import org.ormi.priv.tfa.orderflow.kernel.product.views.ProductView;
import org.ormi.priv.tfa.orderflow.productregistry.read.projection.ProductViewProjector;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.resource.spi.IllegalStateException;
import jakarta.transaction.Transactional;

/**
 * Dispatches product events to appropriate projectors.
 * Coordinates projection updates, persistence, and event broadcasting.
 */

@ApplicationScoped
public class ProjectionDispatcher {
    private static final String PRODUCT_AGGREGATE_TYPE = Product.class.getSimpleName();

    private final Instance<ProductViewProjector> productViewProjector;
    private final ProductViewRepository productViewRepository;
    private final ProductEventBroadcaster productEventBroadcaster;

    @Inject
    public ProjectionDispatcher(
            Instance<ProductViewProjector> productViewProjector,
            ProductViewRepository productViewRepository,
            ProductEventBroadcaster productEventBroadcaster) {
        this.productViewProjector = productViewProjector;
        this.productViewRepository = productViewRepository;
        this.productEventBroadcaster = productEventBroadcaster;
    }

    /**
     * Dispatches product event for projection.
     * 1. Loads current view state
     * 2. Projects event onto view
     * 3. Saves updated view
     * 4. Broadcasts event to subscribers
     * 
     * @param event ProductEventV1Envelope the event to project
     * @return ProjectionResult<ProductView> result of projection operation
     * @throws IllegalStateException if aggregate type doesn't match Product
     */
    @Transactional
    public ProjectionResult<ProductView> dispatch(ProductEventV1Envelope<?> event) throws IllegalStateException {
        if (event.aggregateType().equals(PRODUCT_AGGREGATE_TYPE)) {
            final Optional<ProductView> currentView = productViewRepository
                    .findById(new ProductId(event.aggregateId()));
            final ProjectionResult<ProductView> result = productViewProjector.get().project(currentView, event);
            if (result.isFailure()) {
                // TODO: Hey ! Log the failure. It is not a normal case
                return result;
            }
            if (result.isNoOp()) {
                // TODO: Log info. It may happen if ordering is temporarily broken
            }
            if (result.isSuccess()) {
                productViewRepository.save(result.getProjection());
                productEventBroadcaster.broadcast(new ProductStreamElementDto(
                    event.event().eventType(),
                    event.aggregateId().toString(),
                    event.timestamp()
                ));
            }
            return result;
        }
        throw new IllegalStateException("Unmatched aggregate type: " + event.aggregateType());
    }
}