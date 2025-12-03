// EventAttendanceRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.EventAttendance;
import org.example.finalproject.entity.EventAttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, EventAttendanceId> {}
