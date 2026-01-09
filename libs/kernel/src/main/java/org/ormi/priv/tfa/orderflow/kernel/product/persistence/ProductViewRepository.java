package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import java.util.List;
import java.util.Optional;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;
import org.ormi.priv.tfa.orderflow.kernel.product.views.ProductView;

/**
 * product view repository
 */
public interface ProductViewRepository {
    
    /**
     * @param productView ProductView la vue du produit
     */
    void save(ProductView productView);
    
    /**
     * @param id ProductId l'identifiant du produit
     * @return Optional<ProductView> la vue du produit
     */
    Optional<ProductView> findById(ProductId id);
    
    /**
     * @param skuId SkuId l'identifiant SKU
     * @return Optional<ProductView> la vue du produit
     */
    Optional<ProductView> findBySkuId(SkuId skuId);
    
    /**
     * @param skuIdPattern String le motif de recherche du SKU
     * @return long le nombre total d'éléments
     */
    long countPaginatedViewsBySkuIdPattern(String skuIdPattern);
    
    /**
     * @param skuIdPattern String le motif de recherche du SKU
     * @param page int le numéro de page
     * @param size int la taille de la page
     * @return List<ProductView> la liste des vues de produits
     */
    List<ProductView> searchPaginatedViewsOrderBySkuId(String skuIdPattern, int page, int size);
}