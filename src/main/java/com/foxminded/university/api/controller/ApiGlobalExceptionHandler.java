package com.foxminded.university.api.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.foxminded.university.api.controller.exception.entity.ExceptionDetail;

@RestControllerAdvice(basePackages = "com.foxminded.university.api.controller")
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String API_UNHANDLED_EXCEPTION = "REST API reached unhandled exception: %s";
    private static final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    private static final HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        ExceptionDetail exceptionDetail = new ExceptionDetail(
            String.format(API_UNHANDLED_EXCEPTION, e.getMessage()),
            internalServerError,
            ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(exceptionDetail, internalServerError);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedExceptionException(MethodNotAllowedException ex) {
        ExceptionDetail exceptionDetail = new ExceptionDetail(
            String.format(API_UNHANDLED_EXCEPTION, ex.getMessage()),
            methodNotAllowed,
            ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(exceptionDetail, methodNotAllowed);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(
        TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionDetail exceptionDetail = new ExceptionDetail(
            String.format(API_UNHANDLED_EXCEPTION, ex.getMessage()),
            badRequest,
            ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(exceptionDetail, badRequest);
    }
}
