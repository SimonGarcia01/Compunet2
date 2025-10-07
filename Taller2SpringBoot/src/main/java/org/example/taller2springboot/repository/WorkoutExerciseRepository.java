// WorkoutExerciseRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.WorkoutExercise;
import org.example.taller2springboot.entity.WorkoutExerciseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseId> {}
