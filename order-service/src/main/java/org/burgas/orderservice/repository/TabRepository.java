package org.burgas.orderservice.repository;

import org.burgas.orderservice.entity.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabRepository extends JpaRepository<Tab, Long> {

    Optional<Tab> findTabByIdentityIdAndIsOpenIsTrue(Long identityId);

    Optional<Tab> findTabByUnauthorizedCookieValueAndIsOpenIsTrue(String unauthorizedCookieValue);

    Optional<Tab> findTabByIdentityIdAndId(Long identityId, Long tabId);

    Optional<Tab> findTabByUnauthorizedCookieValue(String unauthorizedCookieValue);

    List<Tab> findTabsByIdentityId(Long identityId);
}
