package com.foxminded.university.dao.exception;

public class ConnectToDBException extends RuntimeException {
    public ConnectToDBException(String message) {
        super(message);
    }
}
