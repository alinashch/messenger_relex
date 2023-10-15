package com.example.chat_relex.exceptions;

public class EmailNotVerification extends RuntimeException {

    public EmailNotVerification(String message) {
        super(message);
    }
}