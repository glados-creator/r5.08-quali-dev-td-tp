package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import java.util.List;

import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.OutboxEntity;

/**
 * outbox repository
 */
public interface OutboxRepository {
    
    /**
     * Publishes an outbox entity to the database.
     * @param entity OutboxEntity l'entité outbox
     */
    void publish(OutboxEntity entity);
    
    /**
     * Fetches ready outbox entries ordered by version.
     * @param aggregateType String le type d'agrégat
     * @param limit int la limite
     * @param maxRetries int le nombre maximum de tentatives
     * @return List<OutboxEntity> la liste des entités outbox
     */
    List<OutboxEntity> fetchReadyByAggregateTypeOrderByAggregateVersion(String aggregateType, int limit, int maxRetries);
    
    /**
     * Deletes an outbox entity after successful processing.
     * @param entity OutboxEntity l'entité outbox
     */
    void delete(OutboxEntity entity);
    
    /**
     * Marks an outbox entity as failed.
     * @param entity OutboxEntity l'entité outbox
     * @param err String l'erreur
     */
    void markFailed(OutboxEntity entity, String err);
    
    /**
     * Marks an outbox entity as failed with retry delay.
     * @param entity OutboxEntity l'entité outbox
     * @param err String l'erreur
     * @param retryAfter int le délai avant nouvelle tentative
     */
    void markFailed(OutboxEntity entity, String err, int retryAfter);
}