package com.alquama00s.realtimenotificationservice.controller.advice;


import com.alquama00s.realtimenotificationservice.DTO.ServerError;
import com.alquama00s.realtimenotificationservice.utils.ErrorHandlingUtils;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class RootAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<@NonNull ServerError> handleError(Exception exception){
            var ref = ErrorHandlingUtils.handleException(exception);
            return ResponseEntity.internalServerError().body(
                    ServerError.builder()
                            .message("contact administrator with reference id")
                            .reference(ref)
                            .build());
    }

}
