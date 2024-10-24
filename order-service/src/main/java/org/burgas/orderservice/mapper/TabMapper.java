package org.burgas.orderservice.mapper;

import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.PurchaseRequest;
import org.burgas.orderservice.dto.TabResponse;
import org.burgas.orderservice.entity.Tab;
import org.burgas.orderservice.handler.RestTemplateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TabMapper {

    private final RestTemplateHandler restTemplateHandler;
    private final PurchaseMapper purchaseMapper;

    public Tab toNewTab(PurchaseRequest purchaseRequest) {

        return Tab.builder()
                .isOpen(true)
                .identityId(purchaseRequest.getIdentityId())
                .storeId(purchaseRequest.getStoreId())
                .openDate(LocalDateTime.now())
                .purchases(new ArrayList<>())
                .totalPrice(0)
                .build();
    }

    public Tab toNewUnauthorizedAccountTab(PurchaseRequest purchaseRequest, String cookieValue) {
        return Tab.builder()
                .isOpen(true)
                .unauthorizedCookieValue(cookieValue)
                .storeId(purchaseRequest.getStoreId())
                .openDate(LocalDateTime.now())
                .purchases(new ArrayList<>())
                .totalPrice(0)
                .build();
    }

    public TabResponse toTabResponse(Tab tab) {

        return TabResponse.builder()
                .id(tab.getId())
                .isOpen(tab.getIsOpen())
                .closeDate(tab.getCloseDate())
                .openDate(tab.getOpenDate())
                .totalPrice(tab.getTotalPrice())
                .unauthorizedCookieValue(tab.getUnauthorizedCookieValue())
                .identityResponse(
                        restTemplateHandler.getIdentityByTabId(tab.getId()).getBody()
                )
                .storeResponse(
                        restTemplateHandler.getStoreByStoreId(tab.getStoreId()).getBody()
                )
                .purchaseResponses(
                        tab.getPurchases()
                                .stream().map(purchaseMapper::toPurchaseResponse)
                                .toList()
                )
                .build();
    }
}
