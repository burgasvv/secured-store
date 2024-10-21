package org.burgas.apigateway.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.apigateway.dto.IdentityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "identity-service",
        url = "http://localhost:8888/identities"
)
public interface IdentityClient {

    @GetMapping("/{username}")
    @CircuitBreaker(
            name = "identityClient",
            fallbackMethod = "fallBackGetIdentityByUsername"
    )
    ResponseEntity<IdentityResponse> getIdentityByUsername(
            @PathVariable String username
    );

    @SuppressWarnings("unused")
    default ResponseEntity<IdentityResponse> fallBackGetIdentityByUsername(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }
}
