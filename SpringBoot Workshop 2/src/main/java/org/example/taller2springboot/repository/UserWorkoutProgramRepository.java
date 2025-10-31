// UserWorkoutProgramRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.UserWorkoutId;
import org.example.taller2springboot.entity.UserWorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkoutProgramRepository extends JpaRepository<UserWorkoutProgram, UserWorkoutId> {}
