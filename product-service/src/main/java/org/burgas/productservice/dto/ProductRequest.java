package org.burgas.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private Long id;
    private String name;
    private Integer amount;
    private Integer price;
    private String description;
    private ProductTypeResponse productTypeResponse;
    private List<StoreResponse> storeResponses;
}
