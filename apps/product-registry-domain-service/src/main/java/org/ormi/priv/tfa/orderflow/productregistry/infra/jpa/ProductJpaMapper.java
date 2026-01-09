package org.ormi.priv.tfa.orderflow.productregistry.infra.jpa;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.ormi.priv.tfa.orderflow.kernel.Product;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductIdMapper;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuIdMapper;

/**
 * Mapper for converting between Product domain objects and JPA entities
 */
@Mapper(
    componentModel = "cdi",
    builder = @Builder(disableBuilder = false),
    uses = { ProductIdMapper.class, SkuIdMapper.class },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductJpaMapper {

    /**
     * Converts a JPA entity to a domain object
     * 
     * @param entity ProductEntity l'entité JPA
     * @return Product l'objet domaine
     */
    public abstract Product toDomain(ProductEntity entity);

    /**
     * Updates a JPA entity from a domain object
     * 
     * @param product Product l'objet domaine
     * @param entity ProductEntity l'entité JPA à mettre à jour
     */
    public abstract void updateEntity(Product product, @MappingTarget ProductEntity entity);

    /**
     * Converts a domain object to a JPA entity
     * 
     * @param product Product l'objet domaine
     * @return ProductEntity l'entité JPA
     */
    public abstract ProductEntity toEntity(Product product);
}