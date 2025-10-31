// TrainerTraineeRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.TrainerTrainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerTraineeRepository extends JpaRepository<TrainerTrainee, Integer> {}
