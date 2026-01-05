package org.ormi.priv.tfa.orderflow.productregistry.application;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;

/**
 * Commands for managing products.
 */
public sealed interface ProductCommand {
    
    /** Create a new product. */
    public record RegisterProductCommand(
            String name,
            String description,
            SkuId skuId) implements ProductCommand {
    }

    /** Discontinue a product. */
    public record RetireProductCommand(ProductId productId) implements ProductCommand {
    }

    /** Change product name. */
    public record UpdateProductNameCommand(ProductId productId, String newName) implements ProductCommand {
    }

    /** Change product description. */
    public record UpdateProductDescriptionCommand(ProductId productId, String newDescription) implements ProductCommand {
    }
}