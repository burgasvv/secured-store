package org.burgas.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponse {

    private Long id;
    private IdentityResponse identityResponse;
    private ProductResponse productResponse;
    private Integer amount;
    private LocalDateTime purchaseDateTime;
}
