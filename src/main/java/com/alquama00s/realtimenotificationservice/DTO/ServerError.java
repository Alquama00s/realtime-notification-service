package com.alquama00s.realtimenotificationservice.DTO;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Builder
@Data
public class ServerError {

     private String message;
     private String reference;

     @Builder.Default
     private Timestamp timestamp = Timestamp.from(Instant.now());

}
