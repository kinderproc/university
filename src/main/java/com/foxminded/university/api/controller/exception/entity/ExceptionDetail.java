package com.foxminded.university.api.controller.exception.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ExceptionDetail {
    private final String message;
    private final HttpStatus httpStatus;
    private ZonedDateTime timeStamp = ZonedDateTime.now(ZoneId.systemDefault());;
    private List<FieldValidationError> fieldValidationErrors = new ArrayList<>();

    public ExceptionDetail(String message, HttpStatus httpStatus,
        ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public ExceptionDetail(String message, HttpStatus httpStatus,
        ZonedDateTime timeStamp, List<FieldValidationError> fieldValidationErrors) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.fieldValidationErrors = fieldValidationErrors;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public List<FieldValidationError> getFieldValidationErrors() {
        return fieldValidationErrors;
    }
}
