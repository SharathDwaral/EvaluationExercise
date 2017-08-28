package com.intuit.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sdwaral on 8/25/17.
 */

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No Such Customer")
public class CustomerNotFound extends RuntimeException{

    public CustomerNotFound(String message) {
        super(message);
    }
}
