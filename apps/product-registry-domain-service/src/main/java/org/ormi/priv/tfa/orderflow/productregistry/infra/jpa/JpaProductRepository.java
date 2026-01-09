package org.ormi.priv.tfa.orderflow.productregistry.infra.jpa;

import java.util.Optional;
import java.util.UUID;

import org.ormi.priv.tfa.orderflow.kernel.Product;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductIdMapper;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuIdMapper;
import org.ormi.priv.tfa.orderflow.kernel.product.persistence.ProductRepository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * JPA implementation of ProductRepository.
 * Handles database operations for Product domain objects.
 */
@ApplicationScoped
public class JpaProductRepository implements PanacheRepositoryBase<ProductEntity, UUID>, ProductRepository {

    ProductJpaMapper mapper;
    ProductIdMapper productIdMapper;    
    SkuIdMapper skuIdMapper;

    @Inject
    public JpaProductRepository(ProductJpaMapper mapper, ProductIdMapper productIdMapper, SkuIdMapper skuIdMapper) {
        this.mapper = mapper;
        this.productIdMapper = productIdMapper;
        this.skuIdMapper = skuIdMapper;
    }

    /**
     * Saves or updates a Product in database.
     * If product exists: updates entity with new values.
     * If product doesn't exist: creates new entity.
     * 
     * @param product Product the domain object to save
     */
    @Override
    @Transactional
    public void save(Product product) {
        findByIdOptional(productIdMapper.map(product.getId()))
                .ifPresentOrElse(e -> {
                    mapper.updateEntity(product, e);
                }, () -> {
                    ProductEntity newEntity = mapper.toEntity(product);
                    getEntityManager().merge(newEntity);
                });
    }

    /**
     * Finds Product by its id.
     * 
     * @param id ProductId the product identifier
     * @return Optional<Product> empty if not found
     */
    @Override
    public Optional<Product> findById(ProductId id) {
        return findByIdOptional(productIdMapper.map(id))
                .map(mapper::toDomain);
    }

    /**
     * Checks if a product exists with given SKU id.
     * 
     * @param skuId SkuId the SKU identifier to check
     * @return boolean true if product exists with this SKU
     */
    @Override
    public boolean existsBySkuId(SkuId skuId) {
        return count("skuId", skuIdMapper.map(skuId)) > 0;
    }
}