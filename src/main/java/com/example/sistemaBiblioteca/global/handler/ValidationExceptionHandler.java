package com.example.sistemaBiblioteca.global.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.sistemaBiblioteca.global.exception.NotEqualsException;

@ControllerAdvice
public class ValidationExceptionHandler {

    private ResponseEntity<Map<String, String>> buildResponseEntity(HttpStatus status, String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", message);
        return new ResponseEntity<>(errorMap, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NotEqualsException.class)
    public ResponseEntity<Map<String, String>> handleNotEqualsException(NotEqualsException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Dados de empréstimo inválidos para devolução.");
    }
}
