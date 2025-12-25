package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;

public class RedisPubSubEventConsumerFactory {


    protected final RedisClient client;


    public RedisPubSubEventConsumerFactory(RedisClient client) {
        this.client = client;
    }


    public <T> RedisPubSubEventConsumer<T> build(Class<T> clazz, String channel, RedisCodec<String,T> codec){

        return RedisPubSubEventConsumer.<T>builder()
                .clazz(clazz)
                .channel(channel)
                .redisCodec(codec)
                .client(client)
                .build();

    }

    public <T> RedisPubSubEventConsumer<T> build(Class<T> clazz, String channel){

        return RedisPubSubEventConsumer.<T>builder()
                .clazz(clazz)
                .channel(channel)
                .client(client)
                .build();

    }

}
