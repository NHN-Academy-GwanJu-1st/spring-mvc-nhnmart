package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.AccountNotFoundException;
import com.nhnacademy.springmvc.exception.PostNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    public String handleAccountNotFoundException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        return "view/error";
    }

    @ExceptionHandler(ValidationFailedException.class)
    public String handleValidationFailedException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        return "view/error";
    }

    @ExceptionHandler(PostNotFoundException.class)
    public String handlePostNotFoundException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        return "view/error";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public String handleFileNotFoundException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        return "view/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("exception", ex);
        return "view/error";
    }
}
