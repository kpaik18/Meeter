package com.example.Meeter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
