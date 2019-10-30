package com.stackoverflow.qanda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "user is unauthorized")
public class UnauthorizedException extends RuntimeException {

}
