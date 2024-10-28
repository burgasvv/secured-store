package org.burgas.paymentservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.dto.PaymentResponse;
import org.burgas.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/identity/{identity-id}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByIdentityId(
            @PathVariable(name = "identity-id") Long identityId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(paymentService.findPaymentsByIdentityId(identityId, request));
    }

    @GetMapping("/identity/{identity-id}/{payment-id}")
    public ResponseEntity<PaymentResponse> getPaymentByIdentityIdAndPaymentId(
            @PathVariable(name = "identity-id") Long identityId,
            @PathVariable(name = "payment-id") Long paymentId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                paymentService.findPaymentByIdentityIdAndPaymentId(identityId, paymentId, request)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<String> makePayment(
            @RequestParam Long tabId, @RequestParam Long identityId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(paymentService.makePayment(identityId, tabId, request));
    }
}
