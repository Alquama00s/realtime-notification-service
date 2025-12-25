package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;

import java.util.HashMap;

public class DefaultRedisPubSubEventConsumerFactory extends RedisPubSubEventConsumerFactory{

    private final String channel;

    public DefaultRedisPubSubEventConsumerFactory(RedisClient client, String channel) {
        super(client);
        this.channel=channel;
    }

    public <T> RedisPubSubEventConsumer<T> build(Class<T> clazz){

        return RedisPubSubEventConsumer.<T>builder()
                .clazz(clazz)
                .channel(channel)
                .client(client)
                .build();

    }

}
