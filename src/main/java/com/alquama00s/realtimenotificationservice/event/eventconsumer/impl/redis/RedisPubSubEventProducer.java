package com.alquama00s.realtimenotificationservice.event.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.event.InitializationException;
import com.alquama00s.realtimenotificationservice.event.eventproducer.EventProducer;
import com.alquama00s.realtimenotificationservice.event.eventproducer.ProducerException;
import com.alquama00s.realtimenotificationservice.event.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import lombok.Builder;

import java.io.IOException;
import java.util.Optional;


public class RedisPubSubEventProducer<T> implements EventProducer<T> {

    private final RedisClient client;
    private StatefulRedisConnection<String,T> connection;
    private RedisCommands<String,T> commands;
    private final RedisCodec<String,T> redisCodec;
    private boolean initialized = false;
    private final String channel;

    private RedisPubSubEventProducer(String channel,RedisCodec<String,T> redisCodec,RedisClient client) {
        this.client = client;
        this.redisCodec= redisCodec;
        this.channel= channel;
        init(channel);
    }

    public static <T> RedisPubSubEventProducerBuilder<T> builder() {
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

    public static class RedisPubSubEventProducerBuilder<T>{

        private int port=6379;
        private String host="localhost";
        private String channel="pubSub";
        private RedisCodec<String,T> redisCodec;
        private RedisClient client;

        public RedisPubSubEventProducerBuilder<T> host(String host){
            this.host=host;
            return this;
        }

        public RedisPubSubEventProducerBuilder<T> port(int port){
            this.port=port;
            return this;
        }

        public RedisPubSubEventProducerBuilder<T> channel(String channel){
            this.channel=channel;
            return this;
        }

        public RedisPubSubEventProducerBuilder<T> clazz(Class<T> clazz){
            this.redisCodec= new JSONCodec<T>(clazz);
            return this;
        }

        public RedisPubSubEventProducerBuilder<T> redisCodec(RedisCodec<String,T> codec){
            this.redisCodec= codec;
            return this;
        }

        public RedisPubSubEventProducerBuilder<T> client(RedisClient client){
            this.client= client;
            return this;
        }

        public RedisPubSubEventProducer<T> build(){
            if(client==null)
                client = RedisClient.create(host+":"+port);
            return new RedisPubSubEventProducer<>(channel,redisCodec,client);
        }
    }
}
