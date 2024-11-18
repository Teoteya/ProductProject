package com.example.product_project.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@Builder
public class ExceptionDto {
    String message;
    HttpStatus httpStatus;
}