package com.example.sistemaBiblioteca.global.exception;

public class NotEqualsException extends RuntimeException{


    public NotEqualsException() {
    }

    public NotEqualsException(String s) {
        super(s);
    }

    public NotEqualsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEqualsException(Throwable cause) {
        super(cause);
    }
}
