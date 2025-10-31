package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.ExerciseRequest;
import org.example.finalproject.api.v1.dtos.ExerciseResponse;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseRestController {

    // Beans
    @Autowired
    private ExerciseService exerciseService;

    // Get exercise by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getExerciseById(@PathVariable("id") Integer id) {

        try {
            ExerciseResponse exercise = exerciseService.findById(id);
            return ResponseEntity.status(200).body(exercise);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all exercises
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllExercises() {

        var exercises = exerciseService.getAllExercises();
        return ResponseEntity.status(200).body(exercises);

    }

    // Create a new exercise
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createExercise(@RequestBody ExerciseRequest exerciseRequest) {

        try {
            exerciseService.createExercise(exerciseRequest);
            return ResponseEntity.status(200).body(new MsgResp("Exercise created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update an exercise
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateExercise(@PathVariable("id") Integer id, @RequestBody ExerciseRequest exerciseRequest) {

        try {
            exerciseService.updateExercise(id, exerciseRequest);
            return ResponseEntity.status(200).body(new MsgResp("Exercise updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete an exercise
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteExercise(@PathVariable("id") Integer id) {

        try {
            exerciseService.deleteExercise(id);
            return ResponseEntity.status(200).body(new MsgResp("Exercise deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}