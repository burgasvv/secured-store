package org.burgas.notificationservice.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.burgas.notificationservice.dto.PaymentMessage;
import org.burgas.notificationservice.service.CustomJavaMailSender;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CustomJavaMailSender mailSender;

    @KafkaListener(
            groupId = "payment-notification-group-id",
            topics = "payment-notification-topic"
    )
    public PaymentMessage consumePaymentNotificationMessage(PaymentMessage paymentMessage) throws MessagingException {
        log.info("Consumed payment message: {}", paymentMessage);
        mailSender.sendPaymentMessage(paymentMessage);
        return paymentMessage;
    }
}
