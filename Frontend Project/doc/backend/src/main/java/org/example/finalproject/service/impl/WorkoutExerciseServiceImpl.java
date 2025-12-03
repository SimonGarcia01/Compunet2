package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.WorkoutExerciseRequest;
import org.example.finalproject.api.v1.dtos.WorkoutExerciseResponse;
import org.example.finalproject.api.v1.mappers.ExerciseMapper;
import org.example.finalproject.api.v1.mappers.WorkoutExerciseMapper;
import org.example.finalproject.api.v1.mappers.WorkoutProgramMapper;
import org.example.finalproject.entity.*;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.ExerciseRepository;
import org.example.finalproject.repository.UserRepository;
import org.example.finalproject.repository.WorkoutExerciseRepository;
import org.example.finalproject.repository.WorkoutProgramRepository;
import org.example.finalproject.service.WorkoutExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {

    // Beans
    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;
    @Autowired
    private WorkoutExerciseMapper workoutExerciseMapper;
    @Autowired
    private WorkoutProgramRepository workoutProgramRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;


    // Find workout exercise by ID
    // This type of id is not necessary use DTO for it, because is only the type or the wrapper for the id
    @Override
    public WorkoutExerciseResponse findById(Integer workoutId, Integer exerciseId) {

        //Make the id so then it can be used to look for it
        WorkoutExerciseId id = new WorkoutExerciseId();
        id.setWorkoutProgramId(workoutId);
        id.setExerciseId(exerciseId);

        return workoutExerciseRepository.findById(id)
                .map(workoutExerciseMapper::toWorkoutExerciseResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Exercise not found with id: " + id));
    }

    // Get all workout exercises
    @Override
    public List<WorkoutExerciseResponse> getAllWorkoutExercises() {

        return workoutExerciseRepository.findAll().stream()
                .map(workoutExerciseMapper::toWorkoutExerciseResponse)
                .toList();

    }

    // Create a new workout exercise
    @Override
    @Transactional
    public void createWorkoutExercise(WorkoutExerciseRequest workoutExerciseRequest) {

        if (workoutExerciseRequest.getWorkoutProgram() == null ||
                workoutExerciseRequest.getExerciseId() == null ||
                workoutExerciseRequest.getSeries() < 0 ||
                workoutExerciseRequest.getSession() < 0 ||
                workoutExerciseRequest.getAmount() < 0 ) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        WorkoutProgram workout = workoutProgramRepository.findById(workoutExerciseRequest.getWorkoutId())
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + workoutExerciseRequest.getWorkoutProgram()));;

        Exercise exercise = exerciseRepository.findById(workoutExerciseRequest.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with ID: " + workoutExerciseRequest.getExerciseId()));;


        WorkoutExercise newWorkoutExercise = workoutExerciseMapper.toWorkoutExercise(workoutExerciseRequest);
        newWorkoutExercise.setWorkoutProgram(workout);
        newWorkoutExercise.setExercise(exercise);

        workoutExerciseRepository.save(newWorkoutExercise);

    }

    // Update an existing workout exercise
    @Override
    @Transactional
    public void updateWorkoutExercise(Integer workoutId, Integer exerciseId, WorkoutExerciseRequest request) {

        //Make the id so then it can be used to look for it
        WorkoutExerciseId id = new WorkoutExerciseId();
        id.setWorkoutProgramId(workoutId);
        id.setExerciseId(exerciseId);

        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Exercise not found with id: " + id));

        WorkoutProgram workout = workoutProgramRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + workoutId));;

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with ID: " + exerciseId));


        // Update fields if provided
        if (workoutExerciseRepository.findById(id).isPresent()) {
            workoutExercise.setWorkoutProgram(workout);
        }

        if (request.getExerciseId() != null) {
            workoutExercise.setExercise(exercise);
        }

        if(request.getSeries() > 0) {
            workoutExercise.setSeries(request.getSeries());
        }

        if(request.getSession() > 0) {
            workoutExercise.setSession(request.getSession());
        }

        if(request.getAmount() > 0) {
            workoutExercise.setAmount(request.getAmount());
        }

        workoutExerciseRepository.save(workoutExercise);

    }

    // Delete an exercise
    @Override
    public void deleteWorkoutExercise(Integer workoutId, Integer exerciseId) {

        //Make the id so then it can be used to look for it
        WorkoutExerciseId id = new WorkoutExerciseId();
        id.setWorkoutProgramId(workoutId);
        id.setExerciseId(exerciseId);

        workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Exercise not found with id: " + id));

        workoutExerciseRepository.deleteById(id);

    }


}
