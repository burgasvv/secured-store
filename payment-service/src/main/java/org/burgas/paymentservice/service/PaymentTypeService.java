package org.burgas.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentTypeResponse;
import org.burgas.paymentservice.mapper.PaymentTypeMapper;
import org.burgas.paymentservice.repository.PaymentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeMapper paymentTypeMapper;

    public List<PaymentTypeResponse> findAll() {
        return paymentTypeRepository.findAll()
                .stream().map(paymentTypeMapper::toPaymentTypeResponse)
                .toList();
    }

    public PaymentTypeResponse findById(Long paymentTypeId) {
        return paymentTypeRepository.findById(paymentTypeId)
                .map(paymentTypeMapper::toPaymentTypeResponse)
                .orElseGet(PaymentTypeResponse::new);
    }
}
