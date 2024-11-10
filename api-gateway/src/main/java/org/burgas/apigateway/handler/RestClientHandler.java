package org.burgas.apigateway.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.burgas.apigateway.dto.IdentityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class RestClientHandler {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "getIdentityByUsername",
            fallbackMethod = "fallBackGetIdentityByUsername"
    )
    public ResponseEntity<IdentityResponse> getIdentityByUsername(String username) {
        return restClient.get()
                .uri("http://localhost:8888/identities/{username}", username)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityByUsername(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }
}
