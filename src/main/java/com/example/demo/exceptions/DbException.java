package com.example.demo.exceptions;

public class DbException extends RuntimeException {
    public DbException(String message) {
        super(message);
    }
}
