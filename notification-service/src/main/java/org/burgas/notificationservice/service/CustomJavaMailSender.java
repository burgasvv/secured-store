package org.burgas.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.burgas.notificationservice.dto.PaymentMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomJavaMailSender {

    private final JavaMailSender javaMailSender;

    public void sendPaymentMessage(PaymentMessage paymentMessage) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
        mimeMessageHelper.setFrom("admin");
        mimeMessageHelper.setTo(paymentMessage.getTabResponse().getIdentityResponse().getEmail());
        mimeMessageHelper.setReplyTo("burgassme@gmail.com");
        mimeMessageHelper.setText(paymentMessage.toString());
        javaMailSender.send(mimeMessage);
    }
}
