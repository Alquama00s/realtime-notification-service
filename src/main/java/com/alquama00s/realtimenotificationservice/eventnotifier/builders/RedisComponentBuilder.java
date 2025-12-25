package com.alquama00s.realtimenotificationservice.eventnotifier.builders;

import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;


/**
 * this class serves as a starting point in creating any redis component builder
 * this abstracts away all important redis related parameters
 * **/
@SuppressWarnings("unchecked")
public class RedisComponentBuilder<T,K extends RedisComponentBuilder<T,K>> {

    protected int port=6379;
    protected String host="localhost";
    protected String channel="pubSub";
    protected RedisCodec<String,T> redisCodec;
    protected RedisClient client;


    public K host(String host){
        this.host=host;
        return (K)this;
    }

    public K port(int port){
        this.port=port;
        return (K)this;
    }

    public K channel(String channel){
        this.channel=channel;
        return (K)this;
    }

    public K clazz(Class<T> clazz){
        this.redisCodec= new JSONCodec<T>(clazz);
        return (K)this;
    }

    public K redisCodec(RedisCodec<String,T> codec){
        this.redisCodec= codec;
        return (K)this;
    }

    public K client(RedisClient client){
        this.client= client;
        return (K)this;
    }

}
