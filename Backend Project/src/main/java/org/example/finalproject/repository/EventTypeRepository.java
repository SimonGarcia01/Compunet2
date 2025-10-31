package org.example.finalproject.repository;

import org.example.finalproject.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
    Optional<EventType> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
