package com.example.chat_relex.exceptions;

public class WrongInputLoginException extends RuntimeException {
    public WrongInputLoginException() {
        super();
    }

    public WrongInputLoginException(String message) {
        super(message);
    }
}