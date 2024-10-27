package org.burgas.orderservice.repository;

import org.burgas.orderservice.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from purchase p where p.tab_id = ?1
                    """
    )
    List<Purchase> findPurchasesByTabId(Long tabId);

    Optional<Purchase> findPurchasesByIdAndProductId(Long purchaseId, Long productId);

    @Query(
            nativeQuery = true,
            value = """
                    select ps.amount from product_store ps where ps.store_id = ?1 and ps.product_id = ?2
                    """
    )
    Integer findProductAmountByStoreIdAndProductId(Long storeId, Long productId);
}
