package org.burgas.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;
    private Long identityId;
    private Long productId;

    @Column(nullable = false)
    private Integer amount;
    private LocalDateTime purchaseDateTime;

    @ManyToOne
    @JoinColumn(name = "tab_id")
    private Tab tab;
}
