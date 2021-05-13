package com.foxminded.university.dao.exception;

public class CantFindDBDriverException extends RuntimeException {
    public CantFindDBDriverException(String message) {
        super(message);
    }
}
