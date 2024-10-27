package org.burgas.orderservice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/make-unauthorized-account-purchase")
    public ResponseEntity<String> makeUnauthorizedAccountPurchase(
            @RequestBody PurchaseRequest purchaseRequest,
            @CookieValue(name = "unauthorized-cookie") Cookie unauthorizedCookie,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(
                purchaseService.makeUnauthorizedAccountPurchase(
                        purchaseRequest, unauthorizedCookie, request, response
                )
        );
    }

    @DeleteMapping("/delete-unauthorized-account-purchase")
    public ResponseEntity<String> deleteUnauthorizedAccountPurchase(
            @CookieValue(name = "unauthorized-cookie") Cookie unauthorizedCookie,
            @RequestParam Long purchaseId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                purchaseService.deleteUnauthorizedAccountPurchase(unauthorizedCookie, purchaseId, request)
        );
    }

    @PostMapping("/make-purchase")
    public ResponseEntity<String> makePurchase(
            @RequestBody PurchaseRequest purchaseRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(purchaseService.makePurchase(purchaseRequest, request));
    }

    @DeleteMapping("/delete-purchase")
    public ResponseEntity<String> deletePurchase(
            @RequestParam Long purchaseId, @RequestParam Long tabId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(purchaseService.deletePurchase(purchaseId, tabId, request));
    }

    @PutMapping("/increment-purchase-product")
    public ResponseEntity<String> incrementPurchaseProductAmount(
            @RequestParam Long purchaseId, @RequestParam Long productId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(purchaseService.incrementPurchaseProductAmount(purchaseId, productId, request));
    }

    @PutMapping("/decrement-purchase-product")
    public ResponseEntity<String> decrementPurchaseProductAmount(
            @RequestParam Long purchaseId, @RequestParam Long productId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(purchaseService.decrementPurchaseProductAmount(purchaseId, productId, request));
    }
}
