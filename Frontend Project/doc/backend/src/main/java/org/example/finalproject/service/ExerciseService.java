package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.AvailableSpaceRequest;
import org.example.finalproject.api.v1.dtos.AvailableSpaceResponse;
import org.example.finalproject.api.v1.dtos.ExerciseRequest;
import org.example.finalproject.api.v1.dtos.ExerciseResponse;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {

    ExerciseResponse findById(Integer id);
    List<ExerciseResponse> getAllExercises();
    void createExercise(ExerciseRequest exerciseRequest);
    void updateExercise(Integer id, ExerciseRequest exerciseRequest);
    void deleteExercise(Integer id);

}
