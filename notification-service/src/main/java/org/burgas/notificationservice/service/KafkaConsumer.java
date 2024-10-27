package org.burgas.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.burgas.notificationservice.dto.PaymentResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(
            groupId = "identity-payment",
            topics = "identity-payment-notification-topic"
    )
    public void getPaymentResponseFromPaymentService(
            ConsumerRecord<String, PaymentResponse> consumerRecord
    ) {
        System.out.println(consumerRecord.value());
    }
}
