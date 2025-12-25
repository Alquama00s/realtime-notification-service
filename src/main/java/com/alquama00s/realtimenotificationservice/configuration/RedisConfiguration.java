package com.alquama00s.realtimenotificationservice.configuration;

import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.DefaultRedisPubSubEventConsumerFactory;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.RedisPubSubEventConsumerFactory;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.impl.redis.RedisPubSubEventProducer;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("prod")
public class RedisConfiguration {


    @Bean
    public RedisClient redisClient(@Value("${redis.url}") String redisUri){
        log.info("using redis uri: {}",redisUri);
        return RedisClient.create(redisUri);
    }


    @Bean
    public RedisPubSubEventProducer redisPubSubEventProducer(RedisClient client){
        return RedisPubSubEventProducer.builder()
                .channel("default-channel")
                .client(client)
                .build();
    }

    @Bean
    public RedisPubSubEventConsumerFactory eventConsumerFactory(RedisClient client){
        return new DefaultRedisPubSubEventConsumerFactory(client,"default-channel");
    }


}
