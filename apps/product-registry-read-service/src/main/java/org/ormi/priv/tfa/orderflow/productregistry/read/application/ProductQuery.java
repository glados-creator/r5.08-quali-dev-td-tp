package org.ormi.priv.tfa.orderflow.productregistry.read.application;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;

/**
 * product query API
 */
public sealed interface ProductQuery {
    
    /**
     * Query to get a product by its ID
     * 
     * @param productId ProductId l'identifiant du produit
     */
    public record GetProductByIdQuery(ProductId productId) implements ProductQuery {
    }

    /**
     * Query to list products with pagination
     * 
     * @param page int le numéro de page
     * @param size int la taille de la page
     */
    public record ListProductQuery(int page, int size) implements ProductQuery {
    }

    /**
     * Query to list products by SKU ID pattern with pagination
     * 
     * @param skuIdPattern String le motif de recherche du SKU
     * @param page int le numéro de page
     * @param size int la taille de la page
     */
    public record ListProductBySkuIdPatternQuery(String skuIdPattern, int page, int size) implements ProductQuery {
    }
}