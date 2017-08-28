package com.intuit.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sdwaral on 8/25/17.
 */

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No Such Merchant")
public class MerchantNotFound extends RuntimeException{

    public MerchantNotFound(String message) {
        super(message);
    }
}
