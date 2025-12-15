package com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.AbstractRedisComponentBuilder;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisComponent;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.EventProducer;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.ProducerException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;

import java.io.IOException;


public class RedisPubSubEventProducer<T> implements EventProducer<T>, RedisComponent<T> {

    private final RedisClient client;
    private StatefulRedisConnection<String,T> connection;
    private RedisCommands<String,T> commands;
    private final RedisCodec<String,T> redisCodec;
    private boolean initialized = false;
    private final String channel;

    private RedisPubSubEventProducer(String channel, RedisCodec<String, T> redisCodec, RedisClient client) {
        this.client = client;
        this.redisCodec= redisCodec;
        this.channel= channel;
        init(channel);
    }

    public static <T> RedisPubSubEventProducerBuilder<T> builder(){
        return new RedisPubSubEventProducerBuilder<>();
    }

    @Override
    public void produce(T event) throws ProducerException {
        if(!initialized) throw new ProducerException("Not initialized");
        commands.publish(channel,event);
    }

    @Override
    public void init(String channel) throws InitializationException {
        if(initialized) return;
        this.connection = client.connect(redisCodec);
        this.commands = this.connection.sync();
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

    public static class RedisPubSubEventProducerBuilder<T> extends AbstractRedisComponentBuilder<T> {
        @Override
        public RedisComponent<T> build() {
            if(client==null)
                client = RedisClient.create(host+":"+port);
            return new RedisPubSubEventProducer<>(channel,redisCodec,client);
        }
    }

}
