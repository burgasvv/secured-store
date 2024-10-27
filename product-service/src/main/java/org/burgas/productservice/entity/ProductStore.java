package org.burgas.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductStorePK.class)
public class ProductStore implements Serializable {

    @Id
    private Long productId;

    @Id
    private Long storeId;

    private Integer amount;
}
