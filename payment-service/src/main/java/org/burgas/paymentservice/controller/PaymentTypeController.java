package org.burgas.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentTypeResponse;
import org.burgas.paymentservice.service.PaymentTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-types")
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentTypeResponse>> getAllPaymentTypes() {
        return ResponseEntity.ok(paymentTypeService.findAll());
    }

    @GetMapping(
            value = "/{payment-type-id}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentTypeResponse> getPaymentTypeById(
            @PathVariable(name = "payment-type-id") Long paymentTypeId
    ) {
        return ResponseEntity.ok(paymentTypeService.findById(paymentTypeId));
    }
}
