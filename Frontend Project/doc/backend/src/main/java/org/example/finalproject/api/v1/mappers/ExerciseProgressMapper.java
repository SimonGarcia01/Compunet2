package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.ExerciseProgressRequest;
import org.example.finalproject.api.v1.dtos.ExerciseProgressResponse;
import org.example.finalproject.entity.ExerciseProgress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, WorkoutProgramMapper.class, ExerciseMapper.class})
public interface ExerciseProgressMapper {

    ExerciseProgress toExerciseProgress(ExerciseProgressRequest exerciseProgressRequest);
    ExerciseProgressResponse toExerciseProgressResponse(ExerciseProgress exerciseProgress);

}

