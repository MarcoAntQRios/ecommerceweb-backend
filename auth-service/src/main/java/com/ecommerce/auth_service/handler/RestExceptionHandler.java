package com.ecommerce.auth_service.handler;

import com.ecommerce.auth_service.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(CustomErrorResponse.builder()
                        .fecha(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .mensaje(ex.getMessage())
                        .path(request.getDescription(false))
                        .build());
    }



    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(CustomErrorResponse.builder()
                        .fecha(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .mensaje("Correo o contraseña incorrectos")
                        .path(request.getDescription(false))
                        .build());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleBadRequestException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof AuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity
                .status(status)
                .body(CustomErrorResponse.builder()
                        .fecha(LocalDateTime.now())
                        .status(status.value())
                        .mensaje(ex.getMessage())
                        .path(request.getDescription(false))
                        .build());
    }
}
