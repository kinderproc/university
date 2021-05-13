package com.foxminded.university.api.controller.exception.entity;

public class FieldValidationError {
    private String field;
    private Object rejectedValue;
    private String message;

    public FieldValidationError(String field, Object rejectedValue, String message) {
        super();
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getMessage() {
        return message;
    }
}
