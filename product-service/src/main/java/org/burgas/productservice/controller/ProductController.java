package org.burgas.productservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.ProductRequest;
import org.burgas.productservice.dto.ProductResponse;
import org.burgas.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(HttpServletRequest request) {
        return ResponseEntity.ok(productService.findAll(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable("product-id") Long productId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(productService.findById(productId, request));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest productRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(productService.createOrUpdate(productRequest, request));
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestBody ProductRequest productRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(productService.createOrUpdate(productRequest, request));
    }

    @DeleteMapping("/delete/{product-id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("product-id") Long productId
    ) {
        return ResponseEntity.ok(productService.delete(productId));
    }
}
