package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.WorkoutProgramRequest;
import org.example.finalproject.api.v1.dtos.WorkoutProgramResponse;
import org.example.finalproject.api.v1.mappers.UserMapper;
import org.example.finalproject.api.v1.mappers.WorkoutProgramMapper;
import org.example.finalproject.entity.WorkoutProgram;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.WorkoutProgramRepository;
import org.example.finalproject.service.WorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutProgramServiceImpl implements WorkoutProgramService {

    // Beans
    @Autowired
    private WorkoutProgramRepository workoutProgramRepository;
    @Autowired
    private WorkoutProgramMapper workoutProgramMapper;
    @Autowired
    private UserMapper userMapper;


    // Find workout program by ID
    @Override
    public WorkoutProgramResponse findById(Integer id) {
        return workoutProgramRepository.findById(id)
                .map(workoutProgramMapper::toWorkoutProgramResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Program not found with id: " + id));
    }

    // Get all workout programs
    @Override
    public List<WorkoutProgramResponse> getAllWorkoutPrograms() {

        return workoutProgramRepository.findAll().stream()
                .map(workoutProgramMapper::toWorkoutProgramResponse)
                .toList();

    }

    // Create a new workout program
    @Override
    @Transactional
    public void createWorkoutProgram(WorkoutProgramRequest workoutProgramRequest) {

        if (workoutProgramRequest.getName() == null ||
                workoutProgramRequest.getDescription() == null ||
                workoutProgramRequest.getPhotoUrl() == null ||
                workoutProgramRequest.getCreationDate() == null ||
                workoutProgramRequest.isCompleted() ||
                workoutProgramRequest.getUser() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        WorkoutProgram newWorkoutProgram = workoutProgramMapper.toWorkoutProgram(workoutProgramRequest);

        workoutProgramRepository.save(newWorkoutProgram);

    }

    // Update an existing workout program
    @Override
    @Transactional
    public void updateWorkoutProgram(Integer id, WorkoutProgramRequest request) {

        WorkoutProgram workoutProgram = workoutProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Program not found with id: " + id));

        // Update fields if provided
        if (request.getName() != null) {
            workoutProgram.setName(request.getName());
        }

        if(request.getDescription() != null) {
            workoutProgram.setDescription(request.getDescription());
        }

        if(request.getPhotoUrl() != null) {
            workoutProgram.setPhotoUrl(request.getPhotoUrl());
        }

        if(request.getCreationDate() != null) {
            workoutProgram.setCreationDate(request.getCreationDate());
        }

        if(request.isCompleted()) {
            workoutProgram.setCompleted(request.isCompleted());
        }

        if(request.getUser() != null) {
            workoutProgram.setUser(userMapper.toUser(request.getUser()));
        }

        workoutProgramRepository.save(workoutProgram);

    }

    // Delete a workout program
    @Override
    public void deleteWorkoutProgram(Integer id) {

        workoutProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout Program not found with id: " + id));

        workoutProgramRepository.deleteById(id);

    }

}
