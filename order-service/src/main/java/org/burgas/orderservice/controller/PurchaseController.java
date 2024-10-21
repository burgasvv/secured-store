package org.burgas.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.PurchaseRequest;
import org.burgas.orderservice.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/make-purchase")
    public ResponseEntity<String> makePurchase(@RequestBody PurchaseRequest purchaseRequest) {
        return ResponseEntity.ok(purchaseService.makePurchase(purchaseRequest));
    }

    @DeleteMapping("/delete-purchase")
    public ResponseEntity<String> deletePurchase(
            @RequestParam Long purchaseId, @RequestParam Long tabId
    ) {
        return ResponseEntity.ok(purchaseService.deletePurchase(purchaseId, tabId));
    }
}
