package com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;

import java.io.Closeable;

/**
 * EventProducer interface for producing events of type T.
 * @param <T> the type of events to be produced
 */
public interface EventProducer<T> extends Closeable {

    /**
     * Produces an event of type T.
     * @param event the event to be produced
     * @throws ProducerException if there is an error while producing the event
     */
    public void produce(T event)throws ProducerException;

    /**
     * Initializes the producer with the given channel.
     * @param channel the channel to produce events to
     * @throws InitializationException if there is an error while initializing the producer
     */
    public void init(String channel)throws InitializationException;
}
