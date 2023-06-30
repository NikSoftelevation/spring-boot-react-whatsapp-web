package com.springbootwhatspp.exception;

public class ChatException extends RuntimeException {
    public ChatException(String message) {
        super(message);
    }
}