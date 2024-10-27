package org.burgas.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TabResponse {

    private Long id;
    private IdentityResponse identityResponse;
    private StoreResponse storeResponse;
    private Boolean isOpen;
    private Integer totalPrice;
    private List<PurchaseResponse>purchaseResponses;
    private String unauthorizedCookieValue;
    private LocalDateTime openDate;
    private LocalDateTime closeDate;
    private PaymentTypeResponse paymentTypeResponse;
}
