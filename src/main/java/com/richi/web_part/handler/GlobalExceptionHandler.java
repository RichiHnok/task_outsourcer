package com.richi.web_part.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    public ResponseEntity<String> handleForbiddenAccessException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
