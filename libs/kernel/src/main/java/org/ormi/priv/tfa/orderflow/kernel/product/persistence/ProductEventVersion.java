package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1;

/**
 * Versions of product events.
 */
public enum ProductEventVersion {
    V1(ProductEventV1.EVENT_VERSION);

    private final int value;

    /**
     * @param value int the version number
     */
    ProductEventVersion(int value) {
        this.value = value;
    }

    /**
     * @return int the version number
     */
    public int getValue() {
        return value;
    }
}