package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;

import java.io.Closeable;
import java.util.Iterator;
import java.util.function.Consumer;


/**
 * EventConsumer interface for consuming events of type T.
 * this extends Iterator and Closeable interfaces.
 * @param <T> the type of events to be consumed
 */
public interface EventConsumer<T> extends Iterator<T>, Closeable {

    /**
     * Sets the consumer function to process the consumed events.
     * this is optional method and can be used to set a callback function.
     * @param consumer the consumer function
     */
    void setConsumer(Consumer<T> consumer);

    /**
     * Initializes the consumer with the given consumer group ID and channel.
     * @param consumerGroupId the consumer group ID
     * @param channel the channel to consume events from
     * @throws InitializationException if there is an error while initializing the consumer
     */
    void init(String consumerGroupId,String channel)throws InitializationException;

}

