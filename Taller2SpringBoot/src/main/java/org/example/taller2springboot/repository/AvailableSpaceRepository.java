
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.AvailableSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableSpaceRepository extends JpaRepository<AvailableSpace, Integer> {
    Optional<AvailableSpace> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
