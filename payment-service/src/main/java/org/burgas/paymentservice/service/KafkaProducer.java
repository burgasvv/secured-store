package org.burgas.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PaymentResponse> paymentResponseKafkaTemplate;

    public void sendPaymentNotification(PaymentResponse paymentResponse) {
        paymentResponseKafkaTemplate.send("identity-payment-notification-topic", paymentResponse);
    }
}
