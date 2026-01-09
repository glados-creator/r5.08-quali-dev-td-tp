package org.ormi.priv.tfa.orderflow.productregistry.read.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.read.ProductStreamElementDto;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.persistence.ProductViewRepository;
import org.ormi.priv.tfa.orderflow.kernel.product.views.ProductView;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * product read service
 */
@ApplicationScoped
public class ReadProductService {

    private final ProductViewRepository repository;
    private final ProductEventBroadcaster productEventBroadcaster;

    /**
     * @param repository ProductViewRepository le repository des vues de produits
     * @param productEventBroadcaster ProductEventBroadcaster le diffuseur d'événements de produit
     */
    @Inject
    public ReadProductService(
        ProductViewRepository repository,
        ProductEventBroadcaster productEventBroadcaster) {
        this.repository = repository;
        this.productEventBroadcaster = productEventBroadcaster;
    }

    /**
     * @param productId ProductId l'identifiant du produit
     * @return Optional<ProductView> la vue du produit
     */
    public Optional<ProductView> findById(ProductId productId) {
        return repository.findById(productId);
    }

    /**
     * @param skuIdPattern String le motif de recherche du SKU
     * @param page int le numéro de page
     * @param size int la taille de la page
     * @return SearchPaginatedResult le résultat de recherche paginé
     */
    public SearchPaginatedResult searchProducts(String skuIdPattern, int page, int size) {
        return new SearchPaginatedResult(
                repository.searchPaginatedViewsOrderBySkuId(skuIdPattern, page, size),
                repository.countPaginatedViewsBySkuIdPattern(skuIdPattern));
    }

    /**
     * @param productId ProductId l'identifiant du produit
     * @return Multi<ProductStreamElementDto> le flux d'événements du produit
     */
    public Multi<ProductStreamElementDto> streamProductEvents(ProductId productId) {
        return productEventBroadcaster.stream()
                .select().where(e -> e.productId().equals(productId.value().toString()));
    }

    /**
     * @param skuIdPattern String le motif de recherche du SKU
     * @param page int le numéro de page
     * @param size int la taille de la page
     * @return Multi<ProductStreamElementDto> le flux d'événements de la liste de produits
     */
    public Multi<ProductStreamElementDto> streamProductListEvents(String skuIdPattern, int page, int size) {
        final List<ProductView> products = searchProducts(skuIdPattern, page, size).page();
        final List<UUID> productIds = products.stream()
                .map(p -> p.getId().value())
                .toList();
        return productEventBroadcaster.stream()
                .select().where(e -> productIds.contains(UUID.fromString(e.productId())));
    }

    /**
     * @param page List<ProductView> la page de résultats
     * @param total long le total d'éléments
     */
    public record SearchPaginatedResult(List<ProductView> page, long total) {
    }
}