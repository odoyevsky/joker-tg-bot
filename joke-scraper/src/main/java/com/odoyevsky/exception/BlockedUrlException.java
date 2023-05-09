package com.odoyevsky.exception;

public class BlockedUrlException extends RuntimeException {
    public BlockedUrlException(String message) {
        super(message);
    }
}
