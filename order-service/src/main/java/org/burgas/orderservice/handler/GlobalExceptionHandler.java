package org.burgas.orderservice.handler;

import org.burgas.orderservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<String> handleProductOutOfStockException(ProductOutOfStockException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(TabNotFoundException.class)
    public ResponseEntity<String> handleTabNotFoundException(TabNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotAuthorizedException.class)
    public ResponseEntity<String> handleIdentityNotAuthorizedException(IdentityNotAuthorizedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(WrongIdentityPurchaseException.class)
    public ResponseEntity<String> handleWrongIdentityPurchaseException(WrongIdentityPurchaseException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotMatchException.class)
    public ResponseEntity<String> handleIdentityNotMatchException(IdentityNotMatchException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(TabAlreadyDoneException.class)
    public ResponseEntity<String> handleTabAlreadyDoneException(TabAlreadyDoneException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityAuthorizedForPurchase.class)
    public ResponseEntity<String> handleIdentityAuthorizedForPurchase(IdentityAuthorizedForPurchase e) {
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body(e.getMessage());
    }
}
