package org.burgas.notificationservice.dto;

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
    private Long identityId;
    private ProductResponse productResponse;
    private Integer amount;
    private LocalDateTime purchaseDateTime;
}
