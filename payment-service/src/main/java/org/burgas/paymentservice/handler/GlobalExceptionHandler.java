package org.burgas.paymentservice.handler;

import org.burgas.paymentservice.exception.IdentityNotAuthorizedException;
import org.burgas.paymentservice.exception.IdentityNotMatchException;
import org.burgas.paymentservice.exception.TabNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdentityNotAuthorizedException.class)
    public ResponseEntity<String> handleIdentityNotAuthorizedException(IdentityNotAuthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(IdentityNotMatchException.class)
    public ResponseEntity<String> handleIdentityNotMatchException(IdentityNotMatchException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(TabNotFoundException.class)
    public ResponseEntity<String> handleTabNotFoundException(TabNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
}
