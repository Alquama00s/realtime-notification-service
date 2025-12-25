package com.alquama00s.realtimenotificationservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public final class ErrorHandlingUtils {

    public static String handleException(Exception exception){
        String uuid = UUID.randomUUID().toString();
        log.error("reference: {} | exception: {}",uuid,exception.getMessage());
        for (int i = 0; i < Math.min(5, exception.getStackTrace().length); i++) {
            log.error("reference: {} | {}",uuid,exception.getStackTrace()[i].toString());
        }
        return uuid;
    }

}
