package org.burgas.orderservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.IdentityResponse;
import org.burgas.orderservice.dto.PaymentTypeResponse;
import org.burgas.orderservice.dto.ProductResponse;
import org.burgas.orderservice.dto.StoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class RestClientHandler {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "getIdentityByTabId",
            fallbackMethod = "fallBackGetIdentityByTabId"
    )
    public ResponseEntity<IdentityResponse> getIdentityByTabId(Long tabId, HttpServletRequest request) {
        return restClient.get()
                .uri("http://localhost:8888/identities/tab/{tab-id}", tabId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(IdentityResponse.class);
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
        return restClient.get()
                .uri("http://localhost:8888/identities/purchase/{purchase-id}", purchaseId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(IdentityResponse.class);
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
        return restClient.get()
                .uri("http://localhost:9020/products/{product-id}", productId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(ProductResponse.class);
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
        return restClient.get()
                .uri("http://localhost:8888/identities/identity/{identity-id}", identityId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(IdentityResponse.class);
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
        return restClient.get()
                .uri("http://localhost:9010/stores/{store-id}", storeId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(StoreResponse.class);
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
        return restClient.get()
                .uri("http://localhost:9040/payment-types/" + paymentTypeId)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(PaymentTypeResponse.class);
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
        return restClient.get()
                .uri("http://localhost:8765/auth/is-authenticated")
                .header(AUTHORIZATION, httpServletRequest.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(Boolean.class);
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
        return restClient.get()
                .uri("http://localhost:8765/auth/authentication-data")
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve()
                .toEntity(Long.class);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<Long> falBackGetAuthenticationCredentialId(Throwable throwable) {
        return ResponseEntity.ok(0L);
    }
}
