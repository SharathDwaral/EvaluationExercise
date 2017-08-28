package com.intuit.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sdwaral on 8/25/17.
 */

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class PayloadValidationError extends RuntimeException {

    public PayloadValidationError(String message) {

        super("Validation Error: " + message);
    }

}
