// ExerciseProgressRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.ExerciseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseProgressRepository extends JpaRepository<ExerciseProgress, Integer> {
    // Find all progress records for a specific user by email
    List<ExerciseProgress> findByUser_Email(String email);
}

