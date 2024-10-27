package org.burgas.paymentservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IdentityNotAuthorizedException extends RuntimeException{

    private final String message;
}
