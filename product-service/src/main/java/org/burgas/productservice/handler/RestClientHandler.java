package org.burgas.productservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.StoreResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestClientHandler {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "getStoresWithProductsByProductId",
            fallbackMethod = "fallBackGetStoreWithProductsByProductId"
    )
    public ResponseEntity<List<StoreResponse>> getStoresWithProductsByProductId(Long productId, HttpServletRequest request) {
        return restClient.get()
                .uri("http://localhost:9010/stores/stores-with-product/" + productId)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<StoreResponse>> fallBackGetStoreWithProductsByProductId(Throwable throwable) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
