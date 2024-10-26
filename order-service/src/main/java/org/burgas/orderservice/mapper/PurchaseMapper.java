package org.burgas.orderservice.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.PurchaseRequest;
import org.burgas.orderservice.dto.PurchaseResponse;
import org.burgas.orderservice.entity.Purchase;
import org.burgas.orderservice.handler.RestTemplateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseMapper {

    private final RestTemplateHandler restTemplateHandler;

    public Purchase toPurchase(PurchaseRequest purchaseRequest) {
        return Purchase.builder()
                .id(purchaseRequest.getId())
                .amount(purchaseRequest.getAmount())
                .identityId(purchaseRequest.getIdentityId())
                .productId(purchaseRequest.getProductId())
                .purchaseDateTime(LocalDateTime.now())
                .build();
    }

    public PurchaseResponse toPurchaseResponse(Purchase purchase, HttpServletRequest request) {
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .amount(purchase.getAmount())
                .purchaseDateTime(purchase.getPurchaseDateTime())
                .productResponse(
                        restTemplateHandler.getProductByProductId(purchase.getProductId(), request).getBody()
                )
                .identityResponse(
                        restTemplateHandler.getIdentityByIdentityId(purchase.getIdentityId(), request).getBody()
                )
                .build();
    }
}
