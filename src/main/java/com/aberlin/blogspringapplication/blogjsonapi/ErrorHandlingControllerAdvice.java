package com.aberlin.blogspringapplication.blogjsonapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ResponseEntity<List<String>> handleTheException(Exception e) {
        return new ResponseEntity(Collections.singleton(e.getMessage()), HttpStatus.I_AM_A_TEAPOT);
    }
}
