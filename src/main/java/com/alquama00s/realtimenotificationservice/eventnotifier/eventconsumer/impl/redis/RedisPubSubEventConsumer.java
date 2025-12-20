package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.AbstractRedisComponentBuilder;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisComponent;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.EventConsumer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Slf4j
public class RedisPubSubEventConsumer<T> implements EventConsumer<T>,RedisComponent {

    private final RedisClient client;
    private StatefulRedisPubSubConnection<String,T> connection;
    private RedisPubSubCommands<String,T> commands;
    private final RedisCodec<String,T> redisCodec;
    private boolean initialized = false;
    private final String channel;
    private final List<Consumer<T>> consumers = new ArrayList<>();
    private final LinkedBlockingQueue<T> msqQueue= new LinkedBlockingQueue<>();

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
        if(initialized)
            throw new IllegalStateException("Cannot set consumer after initialization");
        this.consumers.add(consumer);
    }

    @Override
    public void init() throws InitializationException {
        if(initialized) return;
        this.connection = client.connectPubSub(redisCodec);
        this.commands = this.connection.sync();
        connection.addListener(createListener());
        commands.subscribe(channel);
        initialized=true;
    }

    @Override
    public void close() throws IOException {
        if(!initialized) return;
        connection.close();
        this.connection =null;
        this.commands=null;
        initialized=false;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        try {
            return msqQueue.take();
        } catch (InterruptedException e) {
            log.error("thread interrupted while polling");
        }
        return null;
    }

    public static class RedisPubSubEventConsumerBuilder<T> extends AbstractRedisComponentBuilder<T> {

        @Override
        public RedisComponent build() {
            return new RedisPubSubEventConsumer<T>(channel,redisCodec,client);
        }
        public RedisPubSubEventConsumer<T> buildConsumer() {
            return new RedisPubSubEventConsumer<T>(channel,redisCodec,client);
        }


    }

    private RedisPubSubListener<String,T> createListener(){
        return new RedisPubSubListener<String, T>() {
            @Override
            public void message(String channel, T message) {
                log.info("Received message: {} from channel: {}", message, channel);
                msqQueue.add(message);
                for(Consumer<T> consumer: consumers){
                    consumer.accept(message);
                }
            }

            @Override
            public void message(String pattern, String channel, T message) {

            }

            @Override
            public void subscribed(String channel, long count) {
                log.info("subscribed to channel:{}",channel);
            }

            @Override
            public void psubscribed(String pattern, long count) {

            }

            @Override
            public void unsubscribed(String channel, long count) {

            }

            @Override
            public void punsubscribed(String pattern, long count) {

            }
        };
    }

}
