// EventAttendanceRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.EventAttendance;
import org.example.taller3mvc.entity.EventAttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAttendanceRepository extends JpaRepository<EventAttendance, EventAttendanceId> {}
