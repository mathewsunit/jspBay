package com.jspBay.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sunit on 4/20/17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public UserNotFoundException(String userName) {
        super("No such item: " + userName);
    }
}