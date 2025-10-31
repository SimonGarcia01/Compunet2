package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.WorkoutProgramRequest;
import org.example.finalproject.api.v1.dtos.WorkoutProgramResponse;

import java.util.List;

public interface WorkoutProgramService {

    WorkoutProgramResponse findById(Integer id);
    List<WorkoutProgramResponse> getAllWorkoutPrograms();
    void createWorkoutProgram(WorkoutProgramRequest workoutProgramRequest);
    void updateWorkoutProgram(Integer id, WorkoutProgramRequest workoutProgramRequest);
    void deleteWorkoutProgram(Integer id);

}
