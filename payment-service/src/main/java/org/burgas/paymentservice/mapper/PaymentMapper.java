package org.burgas.paymentservice.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentMessage;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.entity.Payment;
import org.burgas.paymentservice.handler.RestClientHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapper {

    private final RestClientHandler restClientHandler;

    public PaymentResponse toPaymentResponse(Payment payment, HttpServletRequest request) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .tabResponse(
                        restClientHandler.getTabById(payment.getTabId(), request).getBody()
                )
                .build();
    }

    public PaymentMessage toPaymentMessage(PaymentResponse paymentResponse) {
        return PaymentMessage.builder()
                .id(paymentResponse.getId())
                .tabResponse(paymentResponse.getTabResponse())
                .build();
    }
}
