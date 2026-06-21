package com.ecommerce.ventastec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)

public class StripePaymentException extends RuntimeException{

    public StripePaymentException(String message){
        super(message);
    }
}
