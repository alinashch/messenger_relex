package com.example.chat_relex.exceptions;

public class VerificationExpiredException extends RuntimeException {

    public VerificationExpiredException(String message) {
        super(message);
    }
}