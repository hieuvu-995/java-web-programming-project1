package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomController implements ErrorController {
    @RequestMapping(value = "/error")
    public String error() {
        return "/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
