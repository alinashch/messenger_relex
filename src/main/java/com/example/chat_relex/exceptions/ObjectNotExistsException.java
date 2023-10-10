package com.example.chat_relex.exceptions;

public class ObjectNotExistsException extends RuntimeException {
    public ObjectNotExistsException(String message) {
        super(message);
    }
}