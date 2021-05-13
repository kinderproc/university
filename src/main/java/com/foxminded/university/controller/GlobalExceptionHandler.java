package com.foxminded.university.controller;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.foxminded.university.dao.exception.NoExecuteQueryException;

@ControllerAdvice(basePackages = "com.foxminded.university.controller")
class GlobalExceptionHandler {
    public static final String LINE_MASK = "Application has reached unhandled exception!";
    public static final String DEFAULT_ERROR_VIEW = "error";
    private Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception e) throws Exception {
        logger.error("{}: '{}'", e.getClass().getSimpleName(), e.getMessage());
        String msg = "";

        if (e instanceof NoExecuteQueryException) {
            msg = String.format(LINE_MASK, " with database");
        } else if (e instanceof EntityNotFoundException) {
            msg = String.format(LINE_MASK, "! Record not found");
        } else {
            msg = String.format(LINE_MASK, " with application");
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", msg);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
