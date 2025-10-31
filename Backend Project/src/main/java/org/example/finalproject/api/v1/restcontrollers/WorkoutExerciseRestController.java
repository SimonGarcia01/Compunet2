package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.WorkoutExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/workout_exercises")
public class WorkoutExerciseRestController {

    // Beans
    @Autowired
    private WorkoutExerciseService workoutExerciseService;

    // Get workout exercise by ID
    @GetMapping("/{workoutId}/{execiseId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getWorkoutExerciseById(@PathVariable("workoutId") Integer workoutId, @PathVariable("execiseId") Integer execiseId) {

        try {
            WorkoutExerciseResponse workoutExercise = workoutExerciseService.findById(workoutId,execiseId);
            return ResponseEntity.status(200).body(workoutExercise);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all workout exercises
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllWorkoutExercises() {

        var workoutExercises = workoutExerciseService.getAllWorkoutExercises();
        return ResponseEntity.status(200).body(workoutExercises);

    }

    // Create a new exercise
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createExercise(@RequestBody WorkoutExerciseRequest workoutExerciseRequest) {

        try {
            workoutExerciseService.createWorkoutExercise(workoutExerciseRequest);
            return ResponseEntity.status(200).body(new MsgResp("Workout Exercise created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update an exercise
    @PutMapping("/{workoutId}/{execiseId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateWorkoutExercise(@PathVariable("workoutId") Integer workoutId, @PathVariable("execiseId") Integer execiseId, @RequestBody WorkoutExerciseRequest workoutExerciseRequest) {

        try {
            workoutExerciseService.updateWorkoutExercise(workoutId, execiseId, workoutExerciseRequest);
            return ResponseEntity.status(200).body(new MsgResp("Workout Exercise updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete an exercise
    @DeleteMapping("/{workoutId}/{execiseId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteWorkoutExercise(@PathVariable("workoutId") Integer workoutId, @PathVariable("execiseId") Integer execiseId) {

        try {
            workoutExerciseService.deleteWorkoutExercise(workoutId, execiseId);
            return ResponseEntity.status(200).body(new MsgResp("Workout Exercise deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}