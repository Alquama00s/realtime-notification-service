package com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class CustomStringCodec extends JSONCodec<String>{

    public CustomStringCodec() {
        super(String.class);
    }


    @Override
    public String decodeValue(ByteBuffer bytes) {
        if(bytes==null)return null;
        byte[] tempBytes = new byte[bytes.capacity()];
        bytes.get(tempBytes);
        return new String(tempBytes, StandardCharsets.UTF_8);
    }

    @Override
    public ByteBuffer encodeValue(String value) {
        return ByteBuffer.wrap(value.getBytes(StandardCharsets.UTF_8));
    }
}
