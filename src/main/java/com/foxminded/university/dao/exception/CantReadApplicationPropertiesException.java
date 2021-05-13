package com.foxminded.university.dao.exception;

public class CantReadApplicationPropertiesException extends RuntimeException {
    public CantReadApplicationPropertiesException(String message) {
        super(message);
    }
}
