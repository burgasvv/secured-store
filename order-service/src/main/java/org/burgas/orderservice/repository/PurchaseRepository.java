package org.burgas.orderservice.repository;

import org.burgas.orderservice.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    update product set amount = ?1 where id = ?2
                    """
    )
    void updateProductSetAmount(Integer amount, Long productId);
}
