package com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisComponentBuilder;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisComponent;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.EventProducer;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.ProducerException;
import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;

import java.io.IOException;


public class RedisPubSubEventProducer implements EventProducer<Object> {

    private final RedisClient client;
    private StatefulRedisConnection<String,Object> connection;
    private RedisCommands<String,Object> commands;
    private final RedisCodec<String,Object> redisCodec;
    private boolean initialized = false;
    private final String channel;

    private RedisPubSubEventProducer(String channel, RedisCodec<String, Object> redisCodec, RedisClient client) {
        this.client = client;
        this.redisCodec= redisCodec;
        this.channel= channel;
        init();
    }

    public static  RedisPubSubEventProducerBuilder builder(){
        return new RedisPubSubEventProducerBuilder();
    }

    @Override
    public void produce(Object event) throws ProducerException {
        if(!initialized) throw new ProducerException("Not initialized");
        commands.publish(channel,event);
    }


    public void produce(String chan,Object event) throws ProducerException {
        if(!initialized) throw new ProducerException("Not initialized");
        commands.publish(chan,event);
    }


    public void init() throws InitializationException {
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

    public static class RedisPubSubEventProducerBuilder extends RedisComponentBuilder<Object,RedisPubSubEventProducerBuilder> {

        public RedisPubSubEventProducer build() {
            if(client==null)
                client = RedisClient.create(host+":"+port);
            if(redisCodec==null)
                redisCodec=new JSONCodec<Object>(Object.class);
            return new RedisPubSubEventProducer(channel,redisCodec,client);
        }
    }

}
