// ExerciseRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
