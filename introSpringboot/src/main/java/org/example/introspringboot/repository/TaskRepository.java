package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // finds the tasks that must be executed after some specific day and time
    List<Task> findByExecutionTimeAfter(LocalDateTime startTime);

    // find tasks that are between a range of dates
    List<Task> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    // find tasks that have a null description
    List<Task> findByDescriptionIsNull();
}
