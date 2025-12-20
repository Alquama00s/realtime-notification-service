package com.alquama00s.realtimenotificationservice.eventnotifier.builders;

import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.RedisPubSubEventConsumer;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.impl.redis.RedisPubSubEventProducer;
import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;


/**
 * this class serves as a starting point in creating any redis component builder
 * this abstracts away all important redis related parameters
 * **/
public abstract class AbstractRedisComponentBuilder<T> {

    protected int port=6379;
    protected String host="localhost";
    protected String channel="pubSub";
    protected RedisCodec<String,T> redisCodec;
    protected RedisClient client;


    public AbstractRedisComponentBuilder<T> host(String host){
        this.host=host;
        return this;
    }

    public AbstractRedisComponentBuilder<T> port(int port){
        this.port=port;
        return this;
    }

    public AbstractRedisComponentBuilder<T> channel(String channel){
        this.channel=channel;
        return this;
    }

    public AbstractRedisComponentBuilder<T> clazz(Class<T> clazz){
        this.redisCodec= new JSONCodec<T>(clazz);
        return this;
    }

    public AbstractRedisComponentBuilder<T> redisCodec(RedisCodec<String,T> codec){
        this.redisCodec= codec;
        return this;
    }

    public AbstractRedisComponentBuilder<T> client(RedisClient client){
        this.client= client;
        return this;
    }

    public abstract RedisComponent build();

//    {
//        if(client==null)
//            client = RedisClient.create(host+":"+port);
//        return new RedisPubSubEventConsumer<>(channel,redisCodec,client);
//    }

//    public RedisPubSubEventProducer<T> buildProducer(){
//        if(client==null)
//            client = RedisClient.create(host+":"+port);
//        return new RedisPubSubEventProducer<>(channel,redisCodec,client);
//    }
}
