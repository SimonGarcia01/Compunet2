
package org.example.finalproject.repository;

import org.example.finalproject.entity.AvailableSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailableSpaceRepository extends JpaRepository<AvailableSpace, Integer> {
    Optional<AvailableSpace> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
