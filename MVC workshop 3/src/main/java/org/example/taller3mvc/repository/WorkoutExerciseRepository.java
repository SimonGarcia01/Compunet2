// WorkoutExerciseRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.WorkoutExercise;
import org.example.taller3mvc.entity.WorkoutExerciseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId> {}
