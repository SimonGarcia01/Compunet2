// PrivilegeRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    Optional<Privilege> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
