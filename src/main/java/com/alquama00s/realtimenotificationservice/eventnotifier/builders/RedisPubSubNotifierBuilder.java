package com.alquama00s.realtimenotificationservice.eventnotifier.builders;

import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.RedisPubSubEventConsumer;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.impl.redis.RedisPubSubEventProducer;
import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;

public class RedisPubSubNotifierBuilder<T> {

    private int port=6379;
    private String host="localhost";
    private String channel="pubSub";
    private RedisCodec<String,T> redisCodec;
    private RedisClient client;

    public static <T> RedisPubSubNotifierBuilder<T> builder(){
        return new RedisPubSubNotifierBuilder<>();
    }

    public RedisPubSubNotifierBuilder<T> host(String host){
        this.host=host;
        return this;
    }

    public RedisPubSubNotifierBuilder<T> port(int port){
        this.port=port;
        return this;
    }

    public RedisPubSubNotifierBuilder<T> channel(String channel){
        this.channel=channel;
        return this;
    }

    public RedisPubSubNotifierBuilder<T> clazz(Class<T> clazz){
        this.redisCodec= new JSONCodec<T>(clazz);
        return this;
    }

    public RedisPubSubNotifierBuilder<T> redisCodec(RedisCodec<String,T> codec){
        this.redisCodec= codec;
        return this;
    }

    public RedisPubSubNotifierBuilder<T> client(RedisClient client){
        this.client= client;
        return this;
    }

    public RedisPubSubEventConsumer<T> buildConsumer(){
        if(client==null)
            client = RedisClient.create(host+":"+port);
        return new RedisPubSubEventConsumer<>(channel,redisCodec,client);
    }

    public RedisPubSubEventProducer<T> buildProducer(){
        if(client==null)
            client = RedisClient.create(host+":"+port);
        return new RedisPubSubEventProducer<>(channel,redisCodec,client);
    }
}
