package org.burgas.employeeservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.StoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateHandler {

    private final RestTemplate restTemplate;

    @CircuitBreaker(
            name = "getStoreById",
            fallbackMethod = "fallBackGetStoreById"
    )
    public ResponseEntity<StoreResponse> getStoreById(Long storeId) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:9010/stores/" + storeId),
                StoreResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<StoreResponse> fallBackGetStoreById(Throwable throwable) {
        return ResponseEntity.ok(StoreResponse.builder().build());
    }
}
