package com.michael.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author michaelwang on 2021-03-14
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoAccessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NoAccessException(String userName) {
        super(userName+" doesn't have access to this resource.");
    }
}
