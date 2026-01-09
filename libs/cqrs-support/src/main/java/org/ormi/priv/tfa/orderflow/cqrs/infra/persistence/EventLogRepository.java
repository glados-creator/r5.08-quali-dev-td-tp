package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.EventLogEntity;

/**
 * event log repository
 */
public interface EventLogRepository {
    
    /**
     * @param eventLog EventEnvelope<?> l'enveloppe d'événement
     * @return EventLogEntity l'entité log d'événement
     */
    EventLogEntity append(EventEnvelope<?> eventLog);
}