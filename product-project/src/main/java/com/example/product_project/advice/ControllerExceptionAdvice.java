package com.example.product_project.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.product_project.exception.ProductNotFoundException;
import com.example.product_project.exception.ExceptionDto;
@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    private static final String ERROR_MESSAGE = "{} was caught: ";

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleProductNotFoundException(ProductNotFoundException e) {
        log.error(ERROR_MESSAGE, e.getClass().getSimpleName(), e);
        return ExceptionDto.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}
