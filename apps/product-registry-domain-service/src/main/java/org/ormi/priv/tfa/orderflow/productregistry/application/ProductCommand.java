package org.ormi.priv.tfa.orderflow.productregistry.application;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;

/**
 * Commands for managing products.
 */
public sealed interface ProductCommand {
    
    /**
     * Command to register a new product
     * 
     * @param name String le nom du produit
     * @param description String la description du produit
     * @param skuId SkuId l'identifiant SKU du produit
     */
    public record RegisterProductCommand(
            String name,
            String description,
            SkuId skuId) implements ProductCommand {
    }

    /**
     * Command to retire an existing product
     * 
     * @param productId ProductId l'identifiant du produit à retirer
     */
    public record RetireProductCommand(ProductId productId) implements ProductCommand {
    }

    /**
     * Command to update a product's name
     * 
     * @param productId ProductId l'identifiant du produit à mettre à jour
     * @param newName String le nouveau nom du produit
     */
    public record UpdateProductNameCommand(ProductId productId, String newName) implements ProductCommand {
    }

    /**
     * Command to update a product's description
     * 
     * @param productId ProductId l'identifiant du produit à mettre à jour
     * @param newDescription String la nouvelle description du produit
     */
    public record UpdateProductDescriptionCommand(ProductId productId, String newDescription) implements ProductCommand {
    }
}