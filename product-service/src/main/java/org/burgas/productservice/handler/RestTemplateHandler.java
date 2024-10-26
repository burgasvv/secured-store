package org.burgas.productservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.StoreResponse;
import org.burgas.productservice.interceptor.AuthorizationRestTemplateHttpRequestInterceptor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestTemplateHandler {

    private final RestTemplate restTemplate;

    @CircuitBreaker(
            name = "getStoresWithProductsByProductId",
            fallbackMethod = "fallBackGetStoreWithProductsByProductId"
    )
    public ResponseEntity<List<StoreResponse>> getStoresWithProductsByProductId(Long productId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.exchange(
                URI.create("http://localhost:9010/stores/stores-with-product/" + productId),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<List<StoreResponse>> fallBackGetStoreWithProductsByProductId(Throwable throwable) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
