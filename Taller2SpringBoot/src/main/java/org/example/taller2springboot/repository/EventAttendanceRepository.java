// EventAttendanceRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.EventAttendance;
import org.example.taller2springboot.entity.EventAttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAttendanceRepository extends JpaRepository<EventAttendance, EventAttendanceId> {}
