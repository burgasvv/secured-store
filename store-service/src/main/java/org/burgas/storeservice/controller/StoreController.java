package org.burgas.storeservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.storeservice.dto.StoreRequest;
import org.burgas.storeservice.dto.StoreResponse;
import org.burgas.storeservice.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStore() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @GetMapping("/{store-id}")
    public ResponseEntity<StoreResponse> findStoreById(
            @PathVariable("store-id") Long storeId
    ) {
        return ResponseEntity.ok(storeService.findById(storeId));
    }

    @GetMapping("/employee/{employee-id}")
    public ResponseEntity<StoreResponse> getStoreByEmployeeId(
            @PathVariable("employee-id") Long employeeId
    ) {
        return ResponseEntity.ok(storeService.findStoreByEmployeeId(employeeId));
    }

    @GetMapping("/stores-with-product/{product-id}")
    public ResponseEntity<List<StoreResponse>> getStoresByProductId(
            @PathVariable("product-id") Long productId
    ) {
        return ResponseEntity.ok(storeService.findStoresByProductId(productId));
    }

    @PostMapping("/create")
    public ResponseEntity<StoreResponse> createStore(
            @RequestBody StoreRequest storeRequest
    ) {
        return ResponseEntity.ok(storeService.createOrUpdate(storeRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity<StoreResponse> editStore(
            @RequestBody StoreRequest storeRequest
    ) {
        return ResponseEntity.ok(storeService.createOrUpdate(storeRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteStore(
            @RequestParam Long storeId
    ) {
        return ResponseEntity.ok(storeService.delete(storeId));
    }
}
