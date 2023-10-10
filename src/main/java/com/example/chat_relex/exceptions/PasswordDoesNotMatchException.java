package com.example.chat_relex.exceptions;

public class PasswordDoesNotMatchException extends RuntimeException {
    public PasswordDoesNotMatchException() {
        super();
    }

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}