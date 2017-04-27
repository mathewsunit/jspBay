package com.jspBay.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sunit on 4/20/17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BidNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BidNotFoundException(String itemNumber) {
        super("No such item: " + itemNumber);
    }
}