package org.burgas.paymentservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.IdentityPrincipal;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.dto.TabResponse;
import org.burgas.paymentservice.entity.Payment;
import org.burgas.paymentservice.exception.IdentityNotAuthorizedException;
import org.burgas.paymentservice.exception.IdentityNotMatchException;
import org.burgas.paymentservice.exception.TabNotFoundException;
import org.burgas.paymentservice.handler.RestTemplateHandler;
import org.burgas.paymentservice.mapper.PaymentMapper;
import org.burgas.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RestTemplateHandler restTemplateHandler;
    private final KafkaProducer kafkaProducer;

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public PaymentResponse makePayment(Long identityId, Long tabId, HttpServletRequest request) {
        IdentityPrincipal principal = restTemplateHandler.getPrincipal(request).getBody();

        if (Objects.requireNonNull(principal).getIsAuthenticated()) {

            if (Objects.equals(identityId, principal.getId())) {
                TabResponse tabResponse = restTemplateHandler
                        .getTabByIdentityIdAndTabId(identityId, tabId, request).getBody();

                if (tabResponse != null) {
                    PaymentResponse paymentResponse = paymentMapper.toPaymentResponse(
                            paymentRepository.save(
                                    paymentRepository.save(Payment.builder().tabId(tabResponse.getId()).build())
                            ),
                            request
                    );
                    kafkaProducer.sendPaymentNotification(paymentResponse);
                    return paymentResponse;

                } else
                    throw new TabNotFoundException("Заказ по идентификатору не найден");

            } else
                throw new IdentityNotMatchException("Попытка совершения несанкционированной оплаты");
        }
        throw new IdentityNotAuthorizedException("Пользователь не авторизован для совершения оплаты");
    }
}
