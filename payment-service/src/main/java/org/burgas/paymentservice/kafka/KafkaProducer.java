package org.burgas.paymentservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.burgas.paymentservice.dto.PaymentMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PaymentMessage> kafkaTemplate;

    public void sendPaymentNotificationMessage(PaymentMessage paymentMessage) {
        kafkaTemplate.send("payment-notification-topic", paymentMessage)
                .whenComplete(
                        (stringPaymentMessageSendResult, _) ->
                                log.info(
                                        "Payment-notification message was sent: {}",
                                        stringPaymentMessageSendResult.getProducerRecord().value()
                                )
                );
    }
}
