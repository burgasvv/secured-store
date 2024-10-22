package org.burgas.orderservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.IdentityResponse;
import org.burgas.orderservice.dto.ProductResponse;
import org.burgas.orderservice.dto.StoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateHandler {

    private final RestTemplate restTemplate;

    @CircuitBreaker(
            name = "getIdentityByTabId",
            fallbackMethod = "fallBackGetIdentityByTabId"
    )
    public ResponseEntity<IdentityResponse> getIdentityByTabId(Long tabId) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:8888/identities/tab/" + tabId),
                IdentityResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityByTabId(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(
            name = "getProductByProductId",
            fallbackMethod = "fallBackGetProductByProductId"
    )
    public ResponseEntity<ProductResponse> getProductByProductId(Long productId) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:9020/products/" + productId),
                ProductResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ProductResponse> fallBackGetProductByProductId(Throwable throwable) {
        return ResponseEntity.ok(ProductResponse.builder().build());
    }

    @CircuitBreaker(
            name = "getIdentityByIdentityId",
            fallbackMethod = "fallBackGetIdentityByIdentityId"
    )
    public ResponseEntity<IdentityResponse> getIdentityByIdentityId(Long identityId) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:8888/identities/identity/" + identityId),
                IdentityResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityByIdentityId(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(
            name = "getStoreByStoreId",
            fallbackMethod = "fallBackGetStoreByStoreId"
    )
    public ResponseEntity<StoreResponse> getStoreByStoreId(Long storeId) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:9010/stores/" + storeId),
                StoreResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<StoreResponse> fallBackGetStoreByStoreId(Throwable throwable) {
        return ResponseEntity.ok(StoreResponse.builder().build());
    }

    @CircuitBreaker(
            name = "isAuthenticated",
            fallbackMethod = "fallBackIsAuthenticated"
    )
    public ResponseEntity<Boolean> isAuthenticated() {
        return restTemplate.getForEntity(
                URI.create("https://localhost:8765/is-authenticated"),
                Boolean.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Boolean> fallBackIsAuthenticated(Throwable throwable) {
        return ResponseEntity.ok(false);
    }
}
