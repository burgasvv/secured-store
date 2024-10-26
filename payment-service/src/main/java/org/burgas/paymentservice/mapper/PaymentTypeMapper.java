package org.burgas.paymentservice.mapper;

import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentTypeResponse;
import org.burgas.paymentservice.entity.PaymentType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTypeMapper {

    public PaymentTypeResponse toPaymentTypeResponse(PaymentType paymentType) {
        return PaymentTypeResponse.builder()
                .id(paymentType.getId())
                .name(paymentType.getName())
                .build();
    }
}
