package org.burgas.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    private Long id;
    private Long identityId;
    private Long storeId;
    private Long productId;
    private Integer amount;
}
