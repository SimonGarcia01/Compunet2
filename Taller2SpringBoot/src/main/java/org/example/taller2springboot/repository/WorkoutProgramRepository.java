// WorkoutProgramRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.WorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgram, Integer> {
    Optional<WorkoutProgram> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
