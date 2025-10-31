// ExerciseRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
