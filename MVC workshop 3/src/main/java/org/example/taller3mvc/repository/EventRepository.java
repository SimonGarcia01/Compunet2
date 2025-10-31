// EventRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByNameContainingIgnoreCase(String name);
    List<Event> findByDateTimeStartBetween(LocalDateTime from, LocalDateTime to);
}
