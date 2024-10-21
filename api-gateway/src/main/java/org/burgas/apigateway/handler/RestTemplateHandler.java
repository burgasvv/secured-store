package org.burgas.apigateway.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.burgas.apigateway.dto.IdentityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateHandler {

    private final RestTemplate restTemplate;

    @CircuitBreaker(
            name = "getIdentityByUsername",
            fallbackMethod = "fallBackGetIdentityByUsername"
    )
    public ResponseEntity<IdentityResponse> getIdentityByUsername(String username) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:8888/identities/" + username),
                IdentityResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityByUsername(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }
}
