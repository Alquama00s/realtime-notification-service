package com.alquama00s.realtimenotificationservice.configuration;

import io.lettuce.core.RedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {


    @Bean
    public RedisClient redisClient(@Value("${redis.url}") String redisUri){
        return RedisClient.create(redisUri);
    }


}
