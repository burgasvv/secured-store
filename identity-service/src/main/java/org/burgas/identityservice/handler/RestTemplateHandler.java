package org.burgas.identityservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateHandler {

    private final RestTemplate restTemplate;

    @CircuitBreaker(
            name = "getEmployeeByIdentityId",
            fallbackMethod = "fallBackGetEmployeeByIdentityId"
    )
    public ResponseEntity<EmployeeResponse> getEmployeeByIdentityId(Long identityId) {
        return restTemplate.getForEntity(
                URI.create("http://localhost:9000/employees/identity/" + identityId),
                EmployeeResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<EmployeeResponse> fallBackGetEmployeeByIdentityId(Throwable throwable) {
        return ResponseEntity.ok(EmployeeResponse.builder().build());
    }
}
