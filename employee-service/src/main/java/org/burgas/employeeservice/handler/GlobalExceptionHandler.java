package org.burgas.employeeservice.handler;

import org.burgas.employeeservice.exception.EmployeeNotFoundException;
import org.burgas.employeeservice.exception.IdentityNotAuthenticatedException;
import org.burgas.employeeservice.exception.WrongIdentityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongIdentityException.class)
    public ResponseEntity<String> handleWrongIdentityException(WrongIdentityException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotAuthenticatedException.class)
    public ResponseEntity<String> handleIdentityNotAuthenticatedException(IdentityNotAuthenticatedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
