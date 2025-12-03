package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.WorkoutExerciseRequest;
import org.example.finalproject.api.v1.dtos.WorkoutExerciseResponse;
import org.example.finalproject.entity.WorkoutExerciseId;

import java.util.List;

public interface WorkoutExerciseService {

    WorkoutExerciseResponse findById(Integer workoutId, Integer exerciseId);
    List<WorkoutExerciseResponse> getAllWorkoutExercises();
    void createWorkoutExercise(WorkoutExerciseRequest workoutExerciseRequest);
    void updateWorkoutExercise(Integer workoutId, Integer exerciseId, WorkoutExerciseRequest workoutExerciseRequest);
    void deleteWorkoutExercise(Integer workoutId, Integer exerciseId);

}
