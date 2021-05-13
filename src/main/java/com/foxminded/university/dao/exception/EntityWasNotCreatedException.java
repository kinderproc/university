package com.foxminded.university.dao.exception;

public class EntityWasNotCreatedException extends RuntimeException {
    public EntityWasNotCreatedException(String message) {
        super(message);
    }
}
