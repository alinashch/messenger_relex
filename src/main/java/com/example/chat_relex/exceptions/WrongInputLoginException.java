package com.example.chat_relex.exceptions;

public class WrongInputLoginException extends BaseException{
    public WrongInputLoginException() {
        super();
    }

    public WrongInputLoginException(String message) {
        super(message);
    }
}