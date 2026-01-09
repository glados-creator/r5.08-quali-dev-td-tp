package org.ormi.priv.tfa.orderflow.store.infra.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * DTO for product update operations.
 */
public record UpdateProductDto(String id, UpdateOperation[] operations) {

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateNameOperation.class, name = "UpdateProductName"),
        @JsonSubTypes.Type(value = UpdateDescriptionOperation.class, name = "UpdateProductDescription")
    })
    public interface UpdateOperation {
        /**
         * @return UpdateProductOperationType product DTO operation type
         */
        UpdateProductOperationType type();
    }

    /**
     * @param type UpdateProductOperationType operation type
     * @param payload UpdateNameOperationPayload payload with new name
     */
    @JsonTypeName("UpdateProductName")
    public record UpdateNameOperation(UpdateProductOperationType type, UpdateNameOperationPayload payload) implements UpdateOperation {
        /**
         * @param name String new product name
         */
        public record UpdateNameOperationPayload(String name) {}
    }

    /**
     * @param type UpdateProductOperationType operation type
     * @param payload UpdateDescriptionOperationPayload payload with new description
     */
    @JsonTypeName("UpdateProductDescription")
    public record UpdateDescriptionOperation(UpdateProductOperationType type, UpdateDescriptionOperationPayload payload) implements UpdateOperation {
        /**
         * @param description String new product description
         */
        public record UpdateDescriptionOperationPayload(String description) {}
    }

    /**
     * Types of product DTO update operations.
     */
    public enum UpdateProductOperationType {
        @JsonProperty("UpdateProductName")
        UPDATE_NAME,
        @JsonProperty("UpdateProductDescription")
        UPDATE_DESCRIPTION;
    }
}