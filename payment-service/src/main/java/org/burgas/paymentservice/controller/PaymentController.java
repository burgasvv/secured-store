package org.burgas.paymentservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> makePayment(
            @RequestParam Long tabId, @RequestParam Long identityId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(paymentService.makePayment(identityId, tabId, request));
    }
}
