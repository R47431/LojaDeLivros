package com.example.sistemaBiblioteca.global.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.sistemaBiblioteca.global.exception.FileValidationException;

@ControllerAdvice
public class FileExceptionHandler {

    private ResponseEntity<Map<String, String>> buildResponseEntity(HttpStatus status, String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", message);
        return new ResponseEntity<>(errorMap, status);
    }

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<Map<String, String>> handleFileValidationException(FileValidationException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
