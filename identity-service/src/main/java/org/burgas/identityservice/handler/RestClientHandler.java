package org.burgas.identityservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class RestClientHandler {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "getEmployeeByIdentityId",
            fallbackMethod = "fallBackGetEmployeeByIdentityId"
    )
    public ResponseEntity<EmployeeResponse> getEmployeeByIdentityId(Long identityId, HttpServletRequest request) {
        return restClient.get()
                .uri("http://localhost:9000/employees/identity/{identity-id}", identityId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(EmployeeResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<EmployeeResponse> fallBackGetEmployeeByIdentityId(Throwable throwable) {
        return ResponseEntity.ok(EmployeeResponse.builder().build());
    }

    @CircuitBreaker(
            name = "isAuthenticated",
            fallbackMethod = "fallBackIsAuthenticated"
    )
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest httpServletRequest) {
        return restClient.get()
                .uri("http://localhost:8765/auth/is-authenticated")
                .header(AUTHORIZATION, httpServletRequest.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(Boolean.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Boolean> fallBackIsAuthenticated(Throwable throwable) {
        return ResponseEntity.ok(false);
    }

    @CircuitBreaker(
            name = "getAuthenticationCredentialId",
            fallbackMethod = "falBackGetAuthenticationCredentialId"
    )
    public ResponseEntity<Long> getAuthenticationCredentialId(HttpServletRequest httpServletRequest) {
        return restClient.get()
                .uri("http://localhost:8765/auth/authentication-data")
                .header(AUTHORIZATION, httpServletRequest.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(Long.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Long> falBackGetAuthenticationCredentialId(Throwable throwable) {
        return ResponseEntity.ok(-500L);
    }
}
