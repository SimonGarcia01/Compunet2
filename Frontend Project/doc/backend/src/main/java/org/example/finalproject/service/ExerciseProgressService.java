package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.ExerciseProgressRequest;
import org.example.finalproject.api.v1.dtos.ExerciseProgressResponse;

import java.util.List;

public interface ExerciseProgressService {

    List<ExerciseProgressResponse> getMyProgress();
    List<ExerciseProgressResponse> getStudentProgress(String studentEmail);
    ExerciseProgressResponse findById(Integer id);
    List<ExerciseProgressResponse> getAllExerciseProgress();
    void createExerciseProgress(ExerciseProgressRequest exerciseProgressRequest);
    void updateExerciseProgress(Integer id, ExerciseProgressRequest exerciseProgressRequest);
    void deleteExerciseProgress(Integer id);

}

