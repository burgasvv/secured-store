package org.burgas.identityservice.repository;

import org.burgas.identityservice.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

    Optional<Identity> findIdentityByUsername(String username);

    @Query(
            nativeQuery = true,
            value = """
                    update employee set identity_id = null where identity_id = ?1
                    """
    )
    @Modifying
    void updateEmployeeByIdentityIdSetNull(Long identityId);

    @Query(
            nativeQuery = true,
            value = """
                    select i.* from identity i join tab t on i.id = t.identity_id where t.id = ?1
                    """
    )
    Optional<Identity> findIdentityByTabId(Long tabId);

    @Query(
            nativeQuery = true,
            value = """
                    select i.* from identity i join purchase p on i.id = p.identity_id where p.id = ?1
                    """
    )
    Optional<Identity> findIdentityByPurchaseId(Long purchaseId);
}
