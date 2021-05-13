package com.foxminded.university.dao.exception;

public class SqlResourceClosingException extends RuntimeException {
    public SqlResourceClosingException(String message) {
        super(message);
    }
}
