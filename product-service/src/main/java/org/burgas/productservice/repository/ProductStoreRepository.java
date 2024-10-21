package org.burgas.productservice.repository;

import org.burgas.productservice.entity.ProductStore;
import org.burgas.productservice.entity.ProductStorePK;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStoreRepository
        extends JpaRepository<ProductStore, ProductStorePK>
{

    @Query(
            nativeQuery = true,
            value = """
                    select distinct ps.* from product_store ps where ps.product_id = ?1
                    """
    )
    List<ProductStore> findProductStoresByProductId(Long productId);
}
