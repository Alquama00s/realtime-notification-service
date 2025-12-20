package com.alquama00s.realtimenotificationservice.eventnotifier.builders;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;


/**
 * this interface serves as a marker interface for redis components
 * this is used in the RedisComponentBuilder class
 * **/
public interface RedisComponent {
    /**
     * Initializes the consumer with the given consumer group ID and channel.
     * @throws InitializationException if there is an error while initializing the consumer
     */
    default public void init()throws InitializationException{}
}
