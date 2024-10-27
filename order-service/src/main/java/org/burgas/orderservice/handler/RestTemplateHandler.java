package org.burgas.orderservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.IdentityResponse;
import org.burgas.orderservice.dto.PaymentTypeResponse;
import org.burgas.orderservice.dto.ProductResponse;
import org.burgas.orderservice.dto.StoreResponse;
import org.burgas.orderservice.interceptor.AuthorizationRestTemplateHttpRequestInterceptor;
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
            name = "getIdentityByTabId",
            fallbackMethod = "fallBackGetIdentityByTabId"
    )
    public ResponseEntity<IdentityResponse> getIdentityByTabId(Long tabId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:8888/identities/tab/" + tabId),
                IdentityResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityByTabId(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @SuppressWarnings("unused")
    @CircuitBreaker(
            name = "getIdentityByPurchaseId",
            fallbackMethod = "fallBackGetIdentityByPurchaseId"
    )
    public ResponseEntity<IdentityResponse> getIdentityByPurchaseId(Long purchaseId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:8888/identities/purchase/{purchase-id}"),
                IdentityResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityByPurchaseId(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(
            name = "getProductByProductId",
            fallbackMethod = "fallBackGetProductByProductId"
    )
    public ResponseEntity<ProductResponse> getProductByProductId(Long productId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:9020/products/" + productId),
                ProductResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ProductResponse> fallBackGetProductByProductId(Throwable throwable) {
        return ResponseEntity.ok(ProductResponse.builder().build());
    }

    @SuppressWarnings("unused")
    @CircuitBreaker(
            name = "getIdentityByIdentityId",
            fallbackMethod = "fallBackGetIdentityByIdentityId"
    )
    public ResponseEntity<IdentityResponse> getIdentityByIdentityId(Long identityId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
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
    public ResponseEntity<StoreResponse> getStoreByStoreId(Long storeId, HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
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
            name = "getPaymentTypeResponse",
            fallbackMethod = "fallBackGetPaymentTypeResponse"
    )
    public ResponseEntity<PaymentTypeResponse> getPaymentTypeResponse(
            Long paymentTypeId, HttpServletRequest request
    ) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:9040/payment-types/" + paymentTypeId),
                PaymentTypeResponse.class
        );
    }

    @SuppressWarnings("unused")
    private ResponseEntity<PaymentTypeResponse> fallBackGetPaymentTypeResponse(Throwable throwable) {
        return ResponseEntity.ok(PaymentTypeResponse.builder().build());
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
    public ResponseEntity<Long> getAuthenticationCredentialId(HttpServletRequest request) {
        restTemplate.setInterceptors(
                List.of(new AuthorizationRestTemplateHttpRequestInterceptor(request))
        );
        return restTemplate.getForEntity(
                URI.create("http://localhost:8765/auth/authentication-data"),
                Long.class
        );
    }

    @SuppressWarnings("unused")
    public ResponseEntity<Long> falBackGetAuthenticationCredentialId(Throwable throwable) {
        return ResponseEntity.ok(0L);
    }
}
