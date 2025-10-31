// PrivilegeRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    Optional<Privilege> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
