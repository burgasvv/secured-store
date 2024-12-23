package org.burgas.identityservice.handler;

import org.burgas.identityservice.exception.IdentityNotAuthenticatedException;
import org.burgas.identityservice.exception.IdentityNotFoundException;
import org.burgas.identityservice.exception.WrongIdentityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<String> handleIdentityNotFoundException(IdentityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(WrongIdentityException.class)
    public ResponseEntity<String> handleWrongIdentityException(WrongIdentityException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotAuthenticatedException.class)
    public ResponseEntity<String> handleIdentityNotAuthenticatedException(IdentityNotAuthenticatedException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
