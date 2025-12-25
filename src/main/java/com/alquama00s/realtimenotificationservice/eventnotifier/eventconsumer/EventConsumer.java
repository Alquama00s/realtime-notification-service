package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisComponent;

import java.io.Closeable;
import java.util.Iterator;
import java.util.function.Consumer;


/**
 * EventConsumer interface for consuming events of type T.
 * this extends Iterator and Closeable interfaces.
 * @param <T> the type of events to be consumed
 */
public interface EventConsumer<T> extends Closeable {

    /**
     * Sets the consumer function to process the consumed events.
     * this is optional method and can be used to set a callback function.
     * @param consumer the consumer function
     */
    void setConsumer(Consumer<T> consumer);


}

