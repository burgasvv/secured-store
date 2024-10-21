package org.burgas.productservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.ProductRequest;
import org.burgas.productservice.dto.ProductResponse;
import org.burgas.productservice.mapper.ProductMapper;
import org.burgas.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseGet(ProductResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public ProductResponse createOrUpdate(ProductRequest productRequest) {
        return productMapper.toProductResponse(
                productRepository.save(
                        productMapper.toProduct(productRequest)
                )
        );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String delete(Long productId) {
        productRepository.deleteById(productId);
        return "Товар с идентификатором " + productId + " удален";
    }
}
