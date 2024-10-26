package org.burgas.identityservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.EmployeeResponse;
import org.burgas.identityservice.interceptor.AuthorizationRestTemplateHttpRequestInterceptor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestTemplateHandler {

    private final RestTemplate restTemplate;


    @CircuitBreaker(
            name = "getEmployeeByIdentityId",
            fallbackMethod = "fallBackGetEmployeeByIdentityId"
    )
    public ResponseEntity<EmployeeResponse> getEmployeeByIdentityId(Long identityId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:9000/employees/identity/" + identityId),
                EmployeeResponse.class
        );
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
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(httpServletRequest))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:8765/auth/is-authenticated"),
                Boolean.class
        );
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
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(httpServletRequest))
        );
        return restTemplate.exchange(
                URI.create("http://localhost:8765/auth/authentication-data"),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Long> falBackGetAuthenticationCredentialId(Throwable throwable) {
        return ResponseEntity.ok(-500L);
    }
}
