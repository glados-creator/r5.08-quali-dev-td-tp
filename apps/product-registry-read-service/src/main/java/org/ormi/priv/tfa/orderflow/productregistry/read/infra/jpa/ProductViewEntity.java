package org.ormi.priv.tfa.orderflow.productregistry.read.infra.jpa;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductLifecycle;

import com.fasterxml.jackson.databind.JsonNode;

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
 * product view entity
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(
    schema = "read_product_registry",
    name = "product_view",
    indexes = {
        @Index(name = "idx_prdview_sku", columnList = "sku_id")
    })
public class ProductViewEntity {
    
    /**
     * @param id UUID son id
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;
    
    /**
     * @param version Long la version
     */
    @Column(name = "_version", nullable = false, columnDefinition = "bigint")
    private Long version;
    
    /**
     * @param skuId String son id SKU
     */
    @Column(name = "sku_id", nullable = false, length = 9, unique = true, columnDefinition = "varchar(9)")
    private String skuId;
    
    /**
     * @param name String son nom
     */
    @Column(name = "name", nullable = false, columnDefinition = "text")
    private String name;
    
    /**
     * @param description String sa description
     */
    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;
    
    /**
     * @param status ProductLifecycle son état de cycle de vie
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "text")
    private ProductLifecycle status;
    
    /**
     * @param events JsonNode les événements
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "events", nullable = false, columnDefinition = "jsonb")
    private JsonNode events;
    
    /**
     * @param catalogs JsonNode les catalogues
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "catalogs", nullable = false, columnDefinition = "jsonb")
    private JsonNode catalogs;
    
    /**
     * @param createdAt Instant la date de création
     */
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    private Instant createdAt;
    
    /**
     * @param updatedAt Instant la date de mise à jour
     */
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamptz")
    private Instant updatedAt;
}