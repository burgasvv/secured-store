package org.burgas.employeeservice.repository;

import org.burgas.employeeservice.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from position p join employee e on p.id = e.position_id where e.id = ?1
                    """
    )
    Optional<Position> findPositionByEmployeeId(Long employeeId);
}
