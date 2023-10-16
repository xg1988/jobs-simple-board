package com.chosu.jobssimpleboard.common.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAopConfig {
    @ExceptionHandler(Exception.class)
    public String exception(Exception e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", "0000");
        return "exception";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerException(Exception e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", "0001");
        return "exception";
    }
}
