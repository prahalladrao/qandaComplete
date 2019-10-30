package com.stackoverflow.qanda.exception;


import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
@EnableWebMvc
@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = { ExpiredJwtException.class,RuntimeException.class,IllegalArgumentException.class, IllegalStateException.class ,UnauthorizedException.class,NullPointerException.class,io.jsonwebtoken.ExpiredJwtException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        //ex.printStackTrace();

        if(ex.getClass().equals(ValidationException.class))
            return handleExceptionInternal(ex,ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        else if(ex.getClass().equals(NullPointerException.class))
            return handleExceptionInternal(ex,ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        else if(ex.getClass().equals(io.jsonwebtoken.ExpiredJwtException.class))
            return handleExceptionInternal(ex,ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value
            = {Throwable.class, Exception.class, ClaimJwtException.class})
    protected ResponseEntity<Object> authenticationException(
            Exception ex, WebRequest request)
    {
         return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

}
