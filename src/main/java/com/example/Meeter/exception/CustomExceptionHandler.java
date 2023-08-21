package com.example.Meeter.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> handleBusiness(RuntimeException ex, WebRequest request) {
        String body = null;
        if (ex.getMessage() != null) {
            body = new ObjectMapper().createObjectNode().put("errorCode", ex.getMessage()).toString();
        }
        return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = SecurityViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleSecurityViolation(RuntimeException ex, WebRequest request) {
        SecurityContextHolder.clearContext();
        return handleExceptionInternal(ex, null, HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleExpiredJWT(RuntimeException ex, WebRequest request) {
        SecurityContextHolder.clearContext();
        return handleExceptionInternal(ex, null, HttpHeaders.EMPTY, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handle() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMultipartException(MultipartException e, WebRequest request) {
        return handleExceptionInternal(e, e.getCause().getMessage(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

}
