package org.burgas.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.ProductTypeRequest;
import org.burgas.productservice.dto.ProductTypeResponse;
import org.burgas.productservice.service.ProductTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity<List<ProductTypeResponse>> getAllProductTypes() {
        return ResponseEntity.ok(productTypeService.findAll());
    }

    @GetMapping("/{productType-id}")
    public ResponseEntity<ProductTypeResponse> getProductTypeById(
            @PathVariable("productType-id") Long productTypeId
    ) {
        return ResponseEntity.ok(productTypeService.findById(productTypeId));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductTypeResponse> createProductType(
            @RequestBody ProductTypeRequest productTypeRequest
    ) {
        return ResponseEntity.ok(productTypeService.createOrUpdate(productTypeRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductTypeResponse> updateProductType(
            @RequestBody ProductTypeRequest productTypeRequest
    ) {
        return ResponseEntity.ok(productTypeService.createOrUpdate(productTypeRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProductType(
            @RequestParam Long productTypeId
    ) {
        return ResponseEntity.ok(productTypeService.delete(productTypeId));
    }
}
