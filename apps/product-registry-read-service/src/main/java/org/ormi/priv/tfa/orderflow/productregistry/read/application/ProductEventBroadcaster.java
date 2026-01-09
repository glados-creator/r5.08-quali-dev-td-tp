package org.ormi.priv.tfa.orderflow.productregistry.read.application;

import java.util.concurrent.CopyOnWriteArrayList;

import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.read.ProductStreamElementDto;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.MultiEmitter;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service for broadcasting product events to multiple subscribers.
 * Maintains list of active emitters and broadcasts events to all subscribers.
 * Uses thread-safe CopyOnWriteArrayList for concurrent access.
 */

@ApplicationScoped
public class ProductEventBroadcaster {

    private final CopyOnWriteArrayList<MultiEmitter<? super ProductStreamElementDto>> emitters = new CopyOnWriteArrayList<>();

    /**
     * Broadcasts product event to all active subscribers.
     * 
     * @param element ProductStreamElementDto the event to broadcast
     */
    public void broadcast(ProductStreamElementDto element) {
        emitters.forEach(emitter -> emitter.emit(element));
    }

    /**
     * Creates new event stream for subscribers.
     * Adds emitter to list and removes on termination.
     * 
     * @return Multi<ProductStreamElementDto> the event stream
     */
    public Multi<ProductStreamElementDto> stream() {
        return Multi.createFrom().emitter(emitter -> {
            emitters.add(emitter);
            // TODO: log a debug, "New emitter added"

            // TODO: Hey! remove emitters, my RAM is melting! (and log for debugging)
            // TODO: TODO
            emitter.onTermination(() -> emitters.remove(emitter));
        });
    }

    // TODO: implement [Exercice 5]
    // Create filtered stream for specific product id
    // public Multi<ProductStreamElementDto> streamByProductId(String productId) {
    // }

    // TODO: implement [Exercice 5]
    // Create filtered stream for list of product ids
    // public Multi<ProductStreamElementDto> streamByProductIds(List<String> productIds) {
    // }
}