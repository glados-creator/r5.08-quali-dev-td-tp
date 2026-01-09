package org.ormi.priv.tfa.orderflow.kernel.product;

import java.util.UUID;

import org.ormi.priv.tfa.orderflow.cqrs.DomainEvent;
import org.ormi.priv.tfa.orderflow.kernel.Product;

/**
 * Product events version 1.
 */
public sealed interface ProductEventV1 extends DomainEvent {
    public static final int EVENT_VERSION = 1;

    default String aggregateType() {
        return Product.class.getSimpleName();
    }

    ProductId productId();

    default UUID aggregateId() {
        return productId().value();
    }

    @Override
    default public int version() {
        return EVENT_VERSION;
    }

    public sealed interface ProductEventV1Payload extends DomainEventPayload {
        /**
         * Empty payload for events without data.
         */
        public static final class Empty implements ProductEventV1Payload {}
    }

    /**
     * Event when a product is registered.
     */
    public final class ProductRegistered implements ProductEventV1 {
        private final ProductId productId;
        private final ProductRegisteredPayload payload;

        /**
         * @param productId ProductId product id
         * @param skuId SkuId SKU id
         * @param name String product name
         * @param description String product description
         */
        public ProductRegistered(ProductId productId, SkuId skuId, String name, String description) {
            this.productId = productId;
            this.payload = new ProductRegisteredPayload(skuId.value(), name, description);
        }

        @Override
        public ProductId productId() {
            return productId;
        }

        @Override
        public ProductRegisteredPayload payload() {
            return payload;
        }

        /**
         * @param skuId String SKU id
         * @param name String product name
         * @param description String product description
         */
        public static record ProductRegisteredPayload(
                String skuId,
                String name,
                String description) implements ProductEventV1Payload {
        }
    }

    /**
     * Event when a product is retired.
     */
    public final class ProductRetired implements ProductEventV1 {
        private final ProductId productId;

        /**
         * @param productId ProductId product id
         */
        public ProductRetired(ProductId productId) {
            this.productId = productId;
        }

        @Override
        public ProductId productId() {
            return productId;
        }

        @Override
        public ProductEventV1Payload payload() {
            return new ProductEventV1Payload.Empty();
        }
    }

    /**
     * Event when a product name is updated.
     */
    public final class ProductNameUpdated implements ProductEventV1 {
        private final ProductId productId;
        private final ProductNameUpdatedPayload payload;

        /**
         * @param productId ProductId product id
         * @param oldName String previous name
         * @param newName String updated name
         */
        public ProductNameUpdated(ProductId productId, String oldName, String newName) {
            this.productId = productId;
            this.payload = new ProductNameUpdatedPayload(oldName, newName);
        }

        @Override
        public ProductId productId() {
            return productId;
        }

        @Override
        public ProductNameUpdatedPayload payload() {
            return payload;
        }

        /**
         * @param oldName String previous name
         * @param newName String updated name
         */
        public static record ProductNameUpdatedPayload(
                String oldName,
                String newName) implements ProductEventV1Payload {
        }
    }

    /**
     * Event when a product description is updated.
     */
    public final class ProductDescriptionUpdated implements ProductEventV1 {
        private final ProductId productId;
        private final ProductDescriptionUpdatedPayload payload;

        /**
         * @param productId ProductId product id
         * @param oldDescription String previous description
         * @param newDescription String updated description
         */
        public ProductDescriptionUpdated(ProductId productId, String oldDescription, String newDescription) {
            this.productId = productId;
            this.payload = new ProductDescriptionUpdatedPayload(oldDescription, newDescription);
        }

        @Override
        public ProductId productId() {
            return productId;
        }

        @Override
        public ProductDescriptionUpdatedPayload payload() {
            return payload;
        }

        /**
         * @param oldDescription String previous description
         * @param newDescription String updated description
         */
        public static record ProductDescriptionUpdatedPayload(
                String oldDescription,
                String newDescription) implements ProductEventV1Payload {
        }
    }
}