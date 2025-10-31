// UserWorkoutProgramRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.UserWorkoutId;
import org.example.taller3mvc.entity.UserWorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkoutProgramRepository extends JpaRepository<UserWorkoutProgram, UserWorkoutId> {}
