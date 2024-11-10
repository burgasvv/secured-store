package org.burgas.employeeservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.StoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class RestClientHandler {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "getStoreById",
            fallbackMethod = "fallBackGetStoreById"
    )
    public ResponseEntity<StoreResponse> getStoreById(Long storeId, HttpServletRequest request) {
        return restClient.get()
                .uri("http://localhost:9010/stores/{store-id}", storeId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(StoreResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<StoreResponse> fallBackGetStoreById(Throwable throwable) {
        return ResponseEntity.ok(StoreResponse.builder().build());
    }

    @CircuitBreaker(
            name = "isAuthenticated",
            fallbackMethod = "fallBackIsAuthenticated"
    )
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest httpServletRequest) {
        return restClient.get()
                .uri("http://localhost:8765/auth/is-authenticated")
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
                .retrieve()
                .toEntity(Long.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Long> falBackGetAuthenticationCredentialId(Throwable throwable) {
        return ResponseEntity.ok(-500L);
    }
}
