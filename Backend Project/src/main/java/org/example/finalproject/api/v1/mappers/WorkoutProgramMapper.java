package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.WorkoutProgramRequest;
import org.example.finalproject.api.v1.dtos.WorkoutProgramResponse;
import org.example.finalproject.entity.WorkoutProgram;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkoutProgramMapper {

    public WorkoutProgram toWorkoutProgram(WorkoutProgramRequest workoutProgramRequest);
    public WorkoutProgramResponse toWorkoutProgramResponse(WorkoutProgram workoutProgram);

}
