package com.rodriguesadmar.controlesistema.config.exception;

public class AcessDeniedException extends RuntimeException {
    public AcessDeniedException(String message) {
        super(message);
    }
}
