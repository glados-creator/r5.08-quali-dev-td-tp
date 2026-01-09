package org.ormi.priv.tfa.orderflow.productregistry.application;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.EventLogEntity;
import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.OutboxEntity;
import org.ormi.priv.tfa.orderflow.cqrs.infra.persistence.EventLogRepository;
import org.ormi.priv.tfa.orderflow.cqrs.infra.persistence.OutboxRepository;
import org.ormi.priv.tfa.orderflow.kernel.Product;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRetired;
import org.ormi.priv.tfa.orderflow.kernel.product.persistence.ProductRepository;
import org.ormi.priv.tfa.orderflow.productregistry.application.ProductCommand.RetireProductCommand;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Handles product retirement.
 */
@ApplicationScoped
public class RetireProductService {

    @Inject
    ProductRepository repository;
    
    @Inject
    EventLogRepository eventLog;
    
    @Inject
    OutboxRepository outbox;

    /**
     * Executes the product retirement command.
     * This method performs the following operations dans une transaction:
     * 1. get the product gy cmd id from the repository
     * 2. product.retire (with validation) 
     * 3. save
     * 4. Logs retirement event
     * 5. Publishes dans la outbox 
     * 
     * @param cmd RetireProductCommand the command containing the product identifier to retire
     * @throws IllegalArgumentException if the product with the specified ID is not found
     * @throws IllegalStateException if the product cannot be retired due to business rules
     */
    @Transactional
    public void retire(RetireProductCommand cmd) throws IllegalArgumentException {
        Product product = repository.findById(cmd.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        EventEnvelope<ProductRetired> evt = product.retire();
        repository.save(product);
        // Append event to the log
        final EventLogEntity persistedEvent = eventLog.append(evt);
        // Publish outbox
        outbox.publish(OutboxEntity.Builder()
                .sourceEvent(persistedEvent)
                .build());
    }
}