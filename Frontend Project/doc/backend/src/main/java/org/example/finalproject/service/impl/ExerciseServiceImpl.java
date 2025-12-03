package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.ExerciseRequest;
import org.example.finalproject.api.v1.dtos.ExerciseResponse;
import org.example.finalproject.api.v1.mappers.ExerciseMapper;
import org.example.finalproject.entity.Exercise;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.ExerciseRepository;
import org.example.finalproject.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    // Beans
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseMapper exerciseMapper;

    // Find exercise by ID
    @Override
    public ExerciseResponse findById(Integer id) {
        return exerciseRepository.findById(id)
                .map(exerciseMapper::toExerciseResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + id));
    }

    // Get all exercises
    @Override
    public List<ExerciseResponse> getAllExercises() {

        return exerciseRepository.findAll().stream()
                .map(exerciseMapper::toExerciseResponse)
                .toList();

    }

    // Create a new exercise
    @Override
    @Transactional
    public void createExercise(ExerciseRequest exerciseRequest) {

        if (exerciseRequest.getName() == null ||
                exerciseRequest.getType() == null ||
                exerciseRequest.getDescription() == null ||
                exerciseRequest.getDifficulty() == null ||
                exerciseRequest.getProgressUnit() == null ||
                exerciseRequest.getEstimatedUnitaryCaloriesBurnt().compareTo(BigDecimal.valueOf(0)) != 1 &&
                exerciseRequest.getEstimatedUnitaryCaloriesBurnt().compareTo(BigDecimal.valueOf(0)) != 0) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // Check if a exercise with the same name already exists
        if (exerciseRepository.findByNameIgnoreCase(exerciseRequest.getName()).isPresent()) {
            throw new UniquenessViolationException("An exercise with this name already exists. Try another one.");
        }

        Exercise newExercise = exerciseMapper.toExercise(exerciseRequest);

        exerciseRepository.save(newExercise);

    }

    // Update an existing exercise
    @Override
    @Transactional
    public void updateExercise(Integer id, ExerciseRequest exerciseRequest) {

        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + id));

        // Update fields if provided
        if (exerciseRequest.getName() != null && exerciseRepository.findByNameIgnoreCase(exerciseRequest.getName()).isEmpty()) {
            exercise.setName(exerciseRequest.getName());
        } else {
            throw new UniquenessViolationException("An exercise with this name already exists.");
        }

        if (exerciseRequest.getType() != null) {
            exercise.setType(exerciseRequest.getType());
        }

        if(exerciseRequest.getDescription() != null) {
            exercise.setDescription(exerciseRequest.getDescription());
        }

        if(exerciseRequest.getDifficulty() != null) {
            exercise.setDifficulty(exerciseRequest.getDifficulty());
        }

        if(exerciseRequest.getVideoUrl() != null) {
            exercise.setVideoUrl(exerciseRequest.getVideoUrl());
        }

        if(exerciseRequest.getProgressUnit() != null) {
            exercise.setProgressUnit(   exerciseRequest.getProgressUnit());
        }

        // compare To == 1 means that the first parameter is greater than the second parameter
        if(exerciseRequest.getEstimatedUnitaryCaloriesBurnt().compareTo(BigDecimal.valueOf(0)) == 1) {
            exercise.setEstimatedUnitaryCaloriesBurnt(exerciseRequest.getEstimatedUnitaryCaloriesBurnt());
        }

        exerciseRepository.save(exercise);

    }

    // Delete an exercise
    @Override
    public void deleteExercise(Integer id) {

        exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + id));

        exerciseRepository.deleteById(id);

    }

}