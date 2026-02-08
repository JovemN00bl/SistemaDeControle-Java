package com.rodriguesadmar.controlesistema.config.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
