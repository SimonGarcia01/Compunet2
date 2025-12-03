// WorkoutExerciseRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.WorkoutExercise;
import org.example.finalproject.entity.WorkoutExerciseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId> {
}
