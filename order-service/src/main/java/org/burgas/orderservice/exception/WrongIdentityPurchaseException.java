package org.burgas.orderservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WrongIdentityPurchaseException extends RuntimeException{

    private final String message;
}
