package org.burgas.storeservice.repository;

import org.burgas.storeservice.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select s.* from store s join employee e on s.id = e.store_id where e.id = ?1
                    """
    )
    Optional<Store> findStoreByEmployeeId(Long employeeId);

    @Query(
            nativeQuery = true,
            value = """
                    select distinct s.* from store s
                    join product_store ps on s.id = ps.store_id
                    join product p on p.id = ps.product_id
                    where p.id = ?1 order by s.id
                    """
    )
    List<Store> findStoresByProductId(Long productId);
}
