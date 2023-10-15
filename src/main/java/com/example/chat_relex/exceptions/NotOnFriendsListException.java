package com.example.chat_relex.exceptions;

public class NotOnFriendsListException extends RuntimeException {

    public NotOnFriendsListException(String message) {
        super(message);
    }
}