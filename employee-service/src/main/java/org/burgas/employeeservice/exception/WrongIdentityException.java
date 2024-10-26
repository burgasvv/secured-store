package org.burgas.employeeservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WrongIdentityException extends RuntimeException{

    private final String message;
}
