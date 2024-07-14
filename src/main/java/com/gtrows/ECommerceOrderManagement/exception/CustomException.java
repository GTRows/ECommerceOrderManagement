package com.gtrows.ECommerceOrderManagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends Exception {

    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CustomException(Exception e) {
        super(e);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}