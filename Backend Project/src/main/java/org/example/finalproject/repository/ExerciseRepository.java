// ExerciseRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.AvailableSpace;
import org.example.finalproject.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findById(Integer id);
    Optional<Exercise> findByNameIgnoreCase(String name);
}
