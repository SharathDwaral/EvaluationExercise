package com.intuit.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sdwaral on 8/27/17.
 */

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="No Such Merchant")
public class InvalidHeaderError extends RuntimeException {

    public InvalidHeaderError(String message) {
        super(message);
    }
}
