// WorkoutProgramRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.WorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgram, Integer> {
    Optional<WorkoutProgram> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
