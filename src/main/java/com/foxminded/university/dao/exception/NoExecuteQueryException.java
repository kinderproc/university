package com.foxminded.university.dao.exception;

public class NoExecuteQueryException extends RuntimeException {
    public NoExecuteQueryException(String message) {
        super(message);
    }
}
