package com.example.chat_relex.exceptions;

public class NotActiveUserException extends RuntimeException {

    public NotActiveUserException(String message) {
        super(message);
    }
}