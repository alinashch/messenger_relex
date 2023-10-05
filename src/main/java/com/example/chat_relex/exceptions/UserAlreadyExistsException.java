package com.example.chat_relex.exceptions;

public class UserAlreadyExistsException extends BaseException{
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
