package org.burgas.productservice.mapper;

import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.ProductTypeRequest;
import org.burgas.productservice.dto.ProductTypeResponse;
import org.burgas.productservice.entity.ProductType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeMapper {

    public ProductTypeResponse toProductTypeResponse(ProductType productType) {
        return ProductTypeResponse.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }

    public ProductType toProductType(ProductTypeRequest productTypeRequest) {
        return ProductType.builder()
                .id(productTypeRequest.getId())
                .name(productTypeRequest.getName())
                .build();
    }
}
