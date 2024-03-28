package com.udacity.jwdnd.course1.cloudstorage.handling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public String handleBadUrl(Exception ex) {
        return "redirect:/login";
    }
}
