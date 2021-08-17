package com.hashedin.product.kyeazy.exceptions;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String message) {
        super(message);
    }
}
