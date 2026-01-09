package org.ormi.priv.tfa.orderflow.kernel.product;

import java.time.Instant;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductDescriptionUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductNameUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRegistered;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRetired;

/**
 * product event v1 envelope
 */
public abstract class ProductEventV1Envelope<E extends ProductEventV1> extends EventEnvelope<E> {

    /**
     * @param event E l'événement produit
     * @param sequence Long la séquence
     * @param timestamp Instant l'horodatage
     */
    public ProductEventV1Envelope(E event, Long sequence, Instant timestamp) {
		super(event, sequence, timestamp);
	}

	/**
     * registered event envelope
     */
	public static class ProductRegisteredEnvelope extends ProductEventV1Envelope<ProductRegistered> {
        
        /**
         * @param event ProductRegistered l'événement d'enregistrement
         * @param sequence Long la séquence
         * @param timestamp Instant l'horodatage
         */
        public ProductRegisteredEnvelope(ProductRegistered event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }

    /**
     * retired event envelope
     */
    public static class ProductRetiredEnvelope extends ProductEventV1Envelope<ProductRetired> {
        
        /**
         * @param event ProductRetired l'événement de retrait
         * @param sequence Long la séquence
         * @param timestamp Instant l'horodatage
         */
        public ProductRetiredEnvelope(ProductRetired event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }

    /**
     * name updated event envelope
     */
    public static class ProductNameUpdatedEnvelope extends ProductEventV1Envelope<ProductNameUpdated> {
        
        /**
         * @param event ProductNameUpdated l'événement de mise à jour du nom
         * @param sequence Long la séquence
         * @param timestamp Instant l'horodatage
         */
        public ProductNameUpdatedEnvelope(ProductNameUpdated event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }

    /**
     * description updated event envelope
     */
    public static class ProductDescriptionUpdatedEnvelope extends ProductEventV1Envelope<ProductDescriptionUpdated> {
        
        /**
         * @param event ProductDescriptionUpdated l'événement de mise à jour de la description
         * @param sequence Long la séquence
         * @param timestamp Instant l'horodatage
         */
        public ProductDescriptionUpdatedEnvelope(ProductDescriptionUpdated event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }
}