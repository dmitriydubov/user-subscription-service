package com.example.usersubscriptionservice.error;

public class SubscribeNotExistException extends RuntimeException {
    public SubscribeNotExistException(String message) {
        super(message);
    }
}
