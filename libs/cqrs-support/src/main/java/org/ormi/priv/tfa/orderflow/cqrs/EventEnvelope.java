package org.ormi.priv.tfa.orderflow.cqrs;

import java.time.Instant;
import java.util.UUID;

/**
 * Event envelope containing domain event with metadata.
 */
public class EventEnvelope<E extends DomainEvent> {
    private final E event;
    private final Long sequence;
    private final Instant timestamp;

    /**
     * @param event E the domain event
     * @param sequence Long the event sequence number
     * @param timestamp Instant the event timestamp
     */
    public EventEnvelope(E event, Long sequence, Instant timestamp) {
        this.event = event;
        this.sequence = sequence;
        this.timestamp = timestamp;
    }

    /**
     * @return UUID the aggregate identifier
     */
    public UUID aggregateId() {
        return event.aggregateId();
    }
    
    /**
     * @return String the aggregate type
     */
    public String aggregateType() {
        return event.aggregateType();
    }
    
    /**
     * @return E the domain event
     */
    public E event() {
        return event;
    }
    
    /**
     * @return Long the event sequence number
     */
    public Long sequence() {
        return sequence;
    }
    
    /**
     * @return Instant the event timestamp
     */
    public Instant timestamp() {
        return timestamp;
    }

    /**
     * Creates an event envelope with current timestamp.
     * 
     * @param event E the domain event
     * @param sequence Long the event sequence number
     * @return EventEnvelope<E> the event envelope
     */
    public static <E extends DomainEvent> EventEnvelope<E> with(E event, Long sequence) {
        return new EventEnvelope<>(event, sequence, Instant.now());
    }
}