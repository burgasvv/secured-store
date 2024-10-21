package org.burgas.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tab {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Long identityId;

    @Column(nullable = false)
    private Boolean isOpen;
    private Integer totalPrice;

    @OneToMany(
            mappedBy = "tab",
            cascade = ALL,
            fetch = EAGER
    )
    private List<Purchase>purchases = new ArrayList<>();

    public void removeFromPurchases(Purchase purchase) {
        this.purchases.remove(purchase);
        purchase.setTab(null);
    }

    private LocalDateTime openDate;
    private LocalDateTime closeDate;
}
