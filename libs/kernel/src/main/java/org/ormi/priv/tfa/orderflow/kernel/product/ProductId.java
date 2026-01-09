package org.ormi.priv.tfa.orderflow.kernel.product;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * product id
 */
public record ProductId(@NotNull UUID value) {
    
    /**
     * @return ProductId un nouvel identifiant
     */
    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
}