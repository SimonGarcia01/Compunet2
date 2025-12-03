package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.ExerciseRequest;
import org.example.finalproject.api.v1.dtos.ExerciseResponse;
import org.example.finalproject.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    public Exercise toExercise(ExerciseRequest exerciseRequest);
    public ExerciseResponse toExerciseResponse(Exercise exercise);

}
