package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
    Optional<EventType> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
