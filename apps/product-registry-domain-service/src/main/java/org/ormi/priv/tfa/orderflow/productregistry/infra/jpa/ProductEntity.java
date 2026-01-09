package org.ormi.priv.tfa.orderflow.productregistry.infra.jpa;

import java.util.UUID;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductLifecycle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a product in the database
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(
    schema = "domain",
    name = "products",
    indexes = {
        @Index(name = "ux_products_sku", columnList = "sku_id", unique = true)
    })
public class ProductEntity {
    /**
     * The unique identifier of the product
     * 
     * @param id UUID son id
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    /**
     * The name of the product
     * 
     * @param name String le nom du produit
     */
    @Column(name = "name", nullable = false, columnDefinition = "text")
    private String name;

    /**
     * The description of the product
     * 
     * @param description String sa description
     */
    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    /**
     * The SKU identifier of the product
     * 
     * @param skuId String son id SKU
     */
    @Column(name = "sku_id", nullable = false, updatable = false, length = 9, unique = true, columnDefinition = "varchar(9)")
    private String skuId;

    /**
     * The lifecycle status of the product
     * 
     * @param status ProductLifecycle son état de cycle de vie
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "text")
    private ProductLifecycle status;

    /**
     * The version of the product for optimistic locking
     * 
     * @param version Long la version du produit
     */
    @Column(name = "version", nullable = false, columnDefinition = "bigint")
    private Long version;
}