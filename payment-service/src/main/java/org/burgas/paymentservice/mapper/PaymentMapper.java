package org.burgas.paymentservice.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.entity.Payment;
import org.burgas.paymentservice.handler.RestTemplateHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapper {

    private final RestTemplateHandler restTemplateHandler;

    public PaymentResponse toPaymentResponse(Payment payment, HttpServletRequest request) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .tabResponse(
                        restTemplateHandler.getTabById(payment.getTabId(), request).getBody()
                )
                .build();
    }
}
