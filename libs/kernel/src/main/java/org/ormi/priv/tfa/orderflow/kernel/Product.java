package org.ormi.priv.tfa.orderflow.kernel;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductDescriptionUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductNameUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRetired;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductLifecycle;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * The class for a product
 */
@Getter
public class Product {
    private static final Long INITIAL_VERSION = 1L;

    @NotNull
    private final ProductId id;
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @NotNull
    private final SkuId skuId;
    @NotNull
    private ProductLifecycle status;
    @NotNull
    private Long version;

    /**
     * Private constructor for Product
     * 
     * @param id ProductId son id
     * @param name String le nom du produit
     * @param description String sa description
     * @param skuId SkuId son id SKU
     * @param status ProductLifecycle son état de cycle de vie
     * @param version Long la version du produit
     */
    private Product(
            ProductId id,
            String name,
            String description,
            SkuId skuId,
            ProductLifecycle status,
            Long version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.skuId = skuId;
        this.status = status;
        this.version = version;
    }

    /**
     * Creates a new Product instance
     * 
     * @param name String le nom du produit
     * @param description String sa description
     * @param skuId SkuId son id SKU
     * @return Product le produit créé
     */
    public static Product create(
            String name,
            String description,
            SkuId skuId) {
        return Builder()
                .id(ProductId.newId())
                .name(name)
                .description(description)
                .skuId(skuId)
                .status(ProductLifecycle.ACTIVE)
                .version(INITIAL_VERSION)
                .build();
    }

    /**
     * Returns a new ProductBuilder instance
     * 
     * @return ProductBuilder un builder pour créer un Product
     */
    public static ProductBuilder Builder() {
        return new ProductBuilder();
    }

    /**
     * Updates the product name and returns an event envelope
     * 
     * @param name String le nouveau nom du produit
     * @return EventEnvelope<ProductNameUpdated> l'enveloppe d'événement avec le nom mis à jour
     * @throws IllegalStateException si le produit est retiré
     * @throws ConstraintViolationException si la validation échoue
     */
    public EventEnvelope<ProductNameUpdated> updateName(String name) {
        if (!canUpdateDetails(this)) {
            throw new IllegalStateException("Cannot update a retired product");
        }
        final String oldName = this.name;
        this.name = name;
        this.version++;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final var violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return EventEnvelope.with(new ProductNameUpdated(this.id, oldName, this.name), this.version);
    }

    /**
     * Updates the product description and returns an event envelope
     * 
     * @param description String la nouvelle description du produit
     * @return EventEnvelope<ProductDescriptionUpdated> l'enveloppe d'événement avec la description mise à jour
     * @throws IllegalStateException si le produit est retiré
     * @throws ConstraintViolationException si la validation échoue
     */
    public EventEnvelope<ProductDescriptionUpdated> updateDescription(String description) {
        if (!canUpdateDetails(this)) {
            throw new IllegalStateException("Cannot update the product");
        }
        final String oldDescription = this.description;
        this.description = description;
        this.version++;
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final var violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return EventEnvelope.with(new ProductDescriptionUpdated(this.id, oldDescription, this.description), this.version);
    }

    /**
     * Retires the product and returns an event envelope
     * 
     * @return EventEnvelope<ProductRetired> l'enveloppe d'événement pour le produit retiré
     * @throws IllegalStateException si le produit ne peut pas être retiré
     */
    public EventEnvelope<ProductRetired> retire() {
        if (!canRetire()) {
            throw new IllegalStateException("Cannot retire the product");
        }
        this.status = ProductLifecycle.RETIRED;
        this.version++;
        return EventEnvelope.with(new ProductRetired(this.id), this.version);
    }

    /**
     * Checks if product details can be updated
     * 
     * @param p Product le produit à vérifier
     * @return boolean true si les détails peuvent être mis à jour
     */
    private static boolean canUpdateDetails(Product p) {
        return p.status != ProductLifecycle.RETIRED;
    }

    /**
     * Checks if the product can be retired
     * 
     * @return boolean true si le produit peut être retiré
     */
    private boolean canRetire() {
        if (this.status != ProductLifecycle.ACTIVE) {
            return false;
        }
        return true;
    }

    /**
     * Builder class for Product
     */
    public static final class ProductBuilder {
        private ProductId id;
        private String name;
        private String description;
        private SkuId skuId;
        private ProductLifecycle status;
        private Long version;

        /**
         * Sets the product ID
         * 
         * @param id ProductId son id
         * @return ProductBuilder le builder
         */
        public ProductBuilder id(ProductId id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the product name
         * 
         * @param name String le nom du produit
         * @return ProductBuilder le builder
         */
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the product description
         * 
         * @param description String sa description
         * @return ProductBuilder le builder
         */
        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the SKU ID
         * 
         * @param skuId SkuId son id SKU
         * @return ProductBuilder le builder
         */
        public ProductBuilder skuId(SkuId skuId) {
            this.skuId = skuId;
            return this;
        }

        /**
         * Sets the product lifecycle status
         * 
         * @param status ProductLifecycle son état de cycle de vie
         * @return ProductBuilder le builder
         */
        public ProductBuilder status(ProductLifecycle status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the product version
         * 
         * @param version Long la version du produit
         * @return ProductBuilder le builder
         */
        public ProductBuilder version(Long version) {
            this.version = version;
            return this;
        }

        /**
         * Builds the Product instance
         * 
         * @return Product le produit construit
         * @throws ConstraintViolationException si la validation échoue
         */
        public Product build() throws ConstraintViolationException {
            Product product = new Product(id, name, description, skuId, status, version);
            final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            final var violations = validator.validate(product);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            return product;
        }
    }
}