package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.WorkoutExerciseRequest;
import org.example.finalproject.api.v1.dtos.WorkoutExerciseResponse;
import org.example.finalproject.entity.WorkoutExercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkoutExerciseMapper {

    public WorkoutExercise toWorkoutExercise(WorkoutExerciseRequest workoutExerciseRequest);
    public WorkoutExerciseResponse toWorkoutExerciseResponse(WorkoutExercise workoutExercise);

}
