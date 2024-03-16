package com.example.sistemaBiblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleEntityNullPointerException(NullPointerException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entidade null");
    }

    @ExceptionHandler(NoEqualsException.class)
    public ResponseEntity<?> handleNoEqualsException (NoEqualsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados de empréstimo inválidos para devolução.");
    }
}
