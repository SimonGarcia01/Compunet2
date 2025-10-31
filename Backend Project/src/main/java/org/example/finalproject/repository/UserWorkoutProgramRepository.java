// UserWorkoutProgramRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.UserWorkoutId;
import org.example.finalproject.entity.UserWorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkoutProgramRepository extends JpaRepository<UserWorkoutProgram, UserWorkoutId> {}
