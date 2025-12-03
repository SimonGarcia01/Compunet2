// EventRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByNameContainingIgnoreCase(String name);
    List<Event> findByDateTimeStartBetween(LocalDateTime from, LocalDateTime to);
    boolean existsByName(String name);
    Optional<Event> findByName(String name);
}
