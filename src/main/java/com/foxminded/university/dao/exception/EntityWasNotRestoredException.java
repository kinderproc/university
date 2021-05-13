package com.foxminded.university.dao.exception;

public class EntityWasNotRestoredException extends RuntimeException {
    public EntityWasNotRestoredException(String message) {
        super(message);
    }
}
