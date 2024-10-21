package org.burgas.productservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.ProductTypeRequest;
import org.burgas.productservice.dto.ProductTypeResponse;
import org.burgas.productservice.mapper.ProductTypeMapper;
import org.burgas.productservice.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper productTypeMapper;

    public List<ProductTypeResponse> findAll() {
        return productTypeRepository.findAll()
                .stream().map(productTypeMapper::toProductTypeResponse)
                .toList();
    }

    public ProductTypeResponse findById(Long productTypeId) {
        return productTypeRepository.findById(productTypeId)
                .map(productTypeMapper::toProductTypeResponse)
                .orElseGet(ProductTypeResponse::new);
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public ProductTypeResponse createOrUpdate(ProductTypeRequest productTypeRequest) {
        return productTypeMapper.toProductTypeResponse(
                productTypeRepository.save(
                        productTypeMapper.toProductType(productTypeRequest)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String delete(Long productTypeId) {
        productTypeRepository.deleteById(productTypeId);
        return "Тип продукта с идентификатором " + productTypeId + " удален";
    }
}
