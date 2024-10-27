package org.burgas.paymentservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.IdentityPrincipal;
import org.burgas.paymentservice.dto.TabResponse;
import org.burgas.paymentservice.interceptor.AuthorizationRestTemplateHttpRequestInterceptor;
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
            name = "getPrincipal",
            fallbackMethod = "fallBackGetPrincipal"
    )
    public ResponseEntity<IdentityPrincipal> getPrincipal(HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:8765/auth/principal"),
                IdentityPrincipal.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityPrincipal> fallBackGetPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().isAuthenticated(false).build());
    }

    @CircuitBreaker(
            name = "getTabById",
            fallbackMethod = "fallBackGetTabById"
    )
    public ResponseEntity<TabResponse> getTabById(
            Long tabId, HttpServletRequest request
    ) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:9030/tabs/" + tabId),
                TabResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<TabResponse> fallBackGetTabById(Throwable throwable) {
        return ResponseEntity.ok(TabResponse.builder().build());
    }

    @CircuitBreaker(
            name = "getTabByIdentityIdAndTabId",
            fallbackMethod = "fallBackGetTabByIdentityIdAndTabId"
    )
    public ResponseEntity<TabResponse> getTabByIdentityIdAndTabId(
            Long identityId, Long tabId, HttpServletRequest request
    ) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:9030/tabs/identity/" + identityId + "/" + tabId),
                TabResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<TabResponse> fallBackGetTabByIdentityIdAndTabId(Throwable throwable) {
        return ResponseEntity.ok(TabResponse.builder().build());
    }
}
