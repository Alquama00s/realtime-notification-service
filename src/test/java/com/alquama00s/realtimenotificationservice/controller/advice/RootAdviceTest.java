package com.alquama00s.realtimenotificationservice.controller.advice;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class RootAdviceTest {

    private final RootAdvice rootAdvice = new RootAdvice();
    private final ObjectMapper om = new ObjectMapper();

    @Test
    void testDoesNotThrows(){
        var se = rootAdvice.handleError(new Exception("Test exception")).getBody();
        Assertions.assertNotNull(se.getMessage());
        Assertions.assertNotNull(se.getReference());
        Assertions.assertNotNull(se.getTimestamp());
        var serialized = om.writeValueAsString(se);
        log.info("serialized: {}",serialized);
    }

}
