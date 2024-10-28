package org.burgas.paymentservice.repository;

import org.burgas.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from payment p join tab t on t.id = p.tab_id
                    where t.identity_id = ?1
                    """
    )
    List<Payment> findPaymentsByIdentityId(Long identityId);

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from payment p join tab t on t.id = p.tab_id
                    where t.identity_id = ?1 and p.id = ?2
                    """
    )
    Optional<Payment> findPaymentByIdentityIdAndPaymentId(Long identityId, Long paymentId);
}
