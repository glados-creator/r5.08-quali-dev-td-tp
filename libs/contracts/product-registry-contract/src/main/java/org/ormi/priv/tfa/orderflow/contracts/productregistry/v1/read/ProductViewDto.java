package org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.read;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * DTO for product view with details, catalogs and events.
 */
public record ProductViewDto(
        String id,
        String skuId,
        String name,
        String status,
        String description,
        List<ProductViewDtoCatalog> catalogs,
        List<ProductViewDtoEvent> events,
        String createdAt,
        String updatedAt) {

    /**
     * @param id String product id
     * @param name String catalog name
     */
    public static record ProductViewDtoCatalog(
            String id,
            String name) {
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = ProductViewEventDtoPayload.ProductRegisteredPayloadDto.class, name = "ProductRegistered"),
        @JsonSubTypes.Type(value = ProductViewEventDtoPayload.ProductNameUpdatedPayloadDto.class, name = "ProductNameUpdated"),
        @JsonSubTypes.Type(value = ProductViewEventDtoPayload.ProductDescriptionUpdatedPayloadDto.class, name = "ProductDescriptionUpdated"),
        @JsonSubTypes.Type(value = ProductViewEventDtoPayload.ProductRetiredPayloadDto.class, name = "ProductRetired")
    })
    public sealed interface ProductViewEventDtoPayload {
        /**
         * @param skuId String SKU id
         * @param name String product name
         * @param description String product description
         */
        public record ProductRegisteredPayloadDto(
                String skuId,
                String name,
                String description) implements ProductViewEventDtoPayload {
        }
        
        /**
         * @param oldName String previous name
         * @param newName String updated name
         */
        public record ProductNameUpdatedPayloadDto(
                String oldName,
                String newName) implements ProductViewEventDtoPayload {
        }
        
        /**
         * @param oldDescription String previous description
         * @param newDescription String updated description
         */
        public record ProductDescriptionUpdatedPayloadDto(
                String oldDescription,
                String newDescription) implements ProductViewEventDtoPayload {
        }
        
        /**
         * Empty payload for retirement event.
         */
        public record ProductRetiredPayloadDto() implements ProductViewEventDtoPayload {
        }
    }

    /**
     * @param type ProductViewDtoEventType event type
     * @param timestamp String event timestamp
     * @param sequence Long event sequence
     * @param payload ProductViewEventDtoPayload event payload
     */
    public static record ProductViewDtoEvent(
            ProductViewDtoEventType type,
            String timestamp,
            Long sequence,
            ProductViewEventDtoPayload payload) {
    }

    /**
     * Event types for product history.
     */
    public static enum ProductViewDtoEventType {
        REGISTERED("ProductRegistered"),
        NAME_UPDATED("ProductNameUpdated"),
        DESCRIPTION_UPDATED("ProductDescriptionUpdated"),
        RETIRED("ProductRetired");

        private final String value;

        /**
         * @param value String enum value
         */
        ProductViewDtoEventType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static ProductViewDtoEventType fromValue(String value) {
            for (ProductViewDtoEventType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown " + ProductViewDtoEventType.class.getSimpleName() + ": " + value);
        }
    }
}