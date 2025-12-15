package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.AbstractRedisComponentBuilder;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisComponent;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.EventConsumer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;

import java.io.IOException;
import java.util.function.Consumer;

public class RedisPubSubEventConsumer<T> implements EventConsumer<T>,RedisComponent<T> {

    private final RedisClient client;
    private StatefulRedisConnection<String,T> connection;
    private RedisCommands<String,T> commands;
    private final RedisCodec<String,T> redisCodec;
    private boolean initialized = false;
    private final String channel;

    private RedisPubSubEventConsumer(String channel, RedisCodec<String, T> redisCodec, RedisClient client) {
        this.client = client;
        this.redisCodec = redisCodec;
        this.channel = channel;
    }

    public static <T> RedisPubSubEventConsumerBuilder<T> builder(){
        return new RedisPubSubEventConsumerBuilder<>();
    }

    @Override
    public void setConsumer(Consumer<T> consumer) {

    }

    @Override
    public void init() throws InitializationException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    public static class RedisPubSubEventConsumerBuilder<T> extends AbstractRedisComponentBuilder<T> {

        @Override
        public RedisComponent<T> build() {
            return new RedisPubSubEventConsumer<T>(channel,redisCodec,client);
        }
    }

}
