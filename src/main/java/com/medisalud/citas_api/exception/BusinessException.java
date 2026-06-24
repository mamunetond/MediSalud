package com.medisalud.citas_api.exception;

public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
}
