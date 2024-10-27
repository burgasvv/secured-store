package org.burgas.notificationservice.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.burgas.notificationservice.dto.PaymentMessage;
import org.burgas.notificationservice.service.CustomJavaMailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final CustomJavaMailSender javaMailSender;

    @PostMapping(
            value = "/email-payment-message",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> sendEmailPaymentMessage(@RequestBody PaymentMessage paymentMessage)
            throws MessagingException {

        javaMailSender.sendPaymentMessage(paymentMessage);
        return ResponseEntity.ok("Сообщение отправлено на электронную почту покупателя");
    }
}
