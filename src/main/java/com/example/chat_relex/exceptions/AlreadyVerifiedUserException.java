package com.example.chat_relex.exceptions;

public class AlreadyVerifiedUserException extends RuntimeException {

    public AlreadyVerifiedUserException(String message) {
        super(message);
    }
}