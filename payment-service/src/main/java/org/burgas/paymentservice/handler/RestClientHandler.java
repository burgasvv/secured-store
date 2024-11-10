package org.burgas.paymentservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.IdentityPrincipal;
import org.burgas.paymentservice.dto.PaymentMessage;
import org.burgas.paymentservice.dto.TabResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class RestClientHandler {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "getPrincipal",
            fallbackMethod = "fallBackGetPrincipal"
    )
    public ResponseEntity<IdentityPrincipal> getPrincipal(@SuppressWarnings("unused") HttpServletRequest request) {
        return restClient.get()
                .uri("http://localhost:8765/auth/principal")
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityPrincipal> fallBackGetPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().isAuthenticated(false).build());
    }

    @CircuitBreaker(
            name = "getTabById",
            fallbackMethod = "fallBackGetTabById"
    )
    public ResponseEntity<TabResponse> getTabById(Long tabId, HttpServletRequest request) {
        return restClient.get()
                .uri("http://localhost:9030/tabs/{tab-id}", tabId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(TabResponse.class);
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
        return restClient.get()
                .uri("http://localhost:9030/tabs/identity/{identity-id}/{tab-id}", identityId, tabId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(TabResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<TabResponse> fallBackGetTabByIdentityIdAndTabId(Throwable throwable) {
        return ResponseEntity.ok(TabResponse.builder().build());
    }

    public ResponseEntity<String> sendEmailPaymentMessage(PaymentMessage paymentMessage, HttpServletRequest request) {
        return restClient.post()
                .uri("http://localhost:9050/notifications/email-payment-message")
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .body(paymentMessage)
                .retrieve()
                .toEntity(String.class);
    }
}
