package org.burgas.productservice.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.productservice.dto.ProductRequest;
import org.burgas.productservice.dto.ProductResponse;
import org.burgas.productservice.dto.StoreResponse;
import org.burgas.productservice.entity.Product;
import org.burgas.productservice.entity.ProductStore;
import org.burgas.productservice.entity.ProductType;
import org.burgas.productservice.handler.RestTemplateHandler;
import org.burgas.productservice.repository.ProductStoreRepository;
import org.burgas.productservice.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductStoreRepository productStoreRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper productTypeMapper;
    private final RestTemplateHandler restTemplateHandler;

    public ProductResponse toProductResponse(Product product, HttpServletRequest request) {

        List<StoreResponse> storeResponses = restTemplateHandler
                .getStoresWithProductsByProductId(product.getId(), request).getBody();

        List<ProductStore> productStores = productStoreRepository
                .findProductStoresByProductId(product.getId())
                .stream()
                .sorted(Comparator.comparing(ProductStore::getStoreId))
                .toList();

        if (storeResponses != null && !storeResponses.isEmpty() && !productStores.isEmpty()) {

            for (int i = 0; i < productStores.size(); i++) {
                storeResponses.get(i).setProductAmount(productStores.get(i).getAmount());
            }
        }

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .description(product.getDescription())
                .productTypeResponse(
                        productTypeMapper.toProductTypeResponse(
                                productTypeRepository.findById(product.getProductTypeId())
                                        .orElseGet(ProductType::new)
                        )
                )
                .storeResponses(storeResponses)
                .build();
    }

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .amount(productRequest.getAmount())
                .description(productRequest.getDescription())
                .productTypeId(productRequest.getProductTypeResponse().getId())
                .build();
    }
}
