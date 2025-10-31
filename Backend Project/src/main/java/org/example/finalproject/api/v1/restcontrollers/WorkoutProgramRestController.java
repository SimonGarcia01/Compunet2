package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.WorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/workout_programs")
public class WorkoutProgramRestController {

    // Beans
    @Autowired
    private WorkoutProgramService workoutProgramService;

    // Get workout program by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getWorkoutProgramById(@PathVariable("id") Integer id) {

        try {
            WorkoutProgramResponse workoutProgram = workoutProgramService.findById(id);
            return ResponseEntity.status(200).body(workoutProgram);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all workout programs
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllWorkoutPrograms() {

        var workoutPrograms = workoutProgramService.getAllWorkoutPrograms();
        return ResponseEntity.status(200).body(workoutPrograms);

    }

    // Create a new workout program
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createWorkoutProgram(@RequestBody WorkoutProgramRequest workoutProgramRequest) {

        try {
            workoutProgramService.createWorkoutProgram(workoutProgramRequest);
            return ResponseEntity.status(200).body(new MsgResp("Workout Program created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a workout program
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateWorkoutProgram(@PathVariable("id") Integer id, @RequestBody WorkoutProgramRequest workoutProgramRequest) {

        try {
            workoutProgramService.updateWorkoutProgram(id, workoutProgramRequest);
            return ResponseEntity.status(200).body(new MsgResp("Workout Program updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a workout program
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteWorkoutProgram(@PathVariable("id") Integer id) {

        try {
            workoutProgramService.deleteWorkoutProgram(id);
            return ResponseEntity.status(200).body(new MsgResp("Workout Program deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}