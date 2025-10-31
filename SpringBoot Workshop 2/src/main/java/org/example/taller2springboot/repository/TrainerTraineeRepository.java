// TrainerTraineeRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.TrainerTrainee;
import org.example.taller2springboot.entity.TrainerTraineeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerTraineeRepository extends JpaRepository<TrainerTrainee, TrainerTraineeId> {}
