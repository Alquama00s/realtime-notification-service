package com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs;

import io.lettuce.core.codec.RedisCodec;
import tools.jackson.databind.ObjectMapper;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class JSONCodec<T> implements RedisCodec<String,T> {

    private final ObjectMapper om = new ObjectMapper();

    private final Class<T> clazz;

    public JSONCodec(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String decodeKey(ByteBuffer bytes) {
        if(bytes==null)return null;
        byte[] tempBytes = new byte[bytes.capacity()];
        bytes.get(tempBytes);
        return new String(tempBytes,StandardCharsets.UTF_8);
    }

    @Override
    public T decodeValue(ByteBuffer bytes) {
        if(bytes==null)return null;
        byte[] tempByte = new byte[bytes.capacity()];
        bytes.get(tempByte);
        return om.readValue(tempByte,clazz);
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        if(key==null) return ByteBuffer.wrap(new byte[0]);
        return ByteBuffer.wrap(key.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public ByteBuffer encodeValue(T value) {
        if(value==null) return ByteBuffer.wrap(new byte[0]);
        return ByteBuffer.wrap(om.writeValueAsBytes(value));
    }
}
