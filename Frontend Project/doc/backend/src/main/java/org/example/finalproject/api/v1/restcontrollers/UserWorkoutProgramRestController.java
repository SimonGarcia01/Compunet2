package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.entity.UserRoleId;
import org.example.finalproject.entity.UserWorkoutId;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.UserWorkoutProgramRepository;
import org.example.finalproject.service.UserWorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user_workout_programs")
public class UserWorkoutProgramRestController {

    // Beans
    @Autowired
    private UserWorkoutProgramService userWorkoutProgramService;
    @Autowired
    private UserWorkoutProgramRepository userWorkoutProgramRepository;

    // Get user workout program by ID
    @GetMapping("/{userId}/{workoutId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getUserWorkoutProgramById(@PathVariable("userId") Integer userId, @PathVariable("workoutId") Integer workoutId) {

        try {
            UserWorkoutProgramRequestResponse userWorkoutProgram = userWorkoutProgramService.findById(userId, workoutId);
            return ResponseEntity.status(200).body(userWorkoutProgram);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all user workout programs
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllUserWorkoutPrograms() {

        var userWorkoutPrograms = userWorkoutProgramService.getAllUserWorkoutPrograms();
        return ResponseEntity.status(200).body(userWorkoutPrograms);

    }

    // Create a new user workout program
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createUserRole(@RequestBody UserWorkoutProgramRequestResponse userWorkoutProgramRequest) {

        try {
            userWorkoutProgramService.createUserWorkoutProgram(userWorkoutProgramRequest);
            return ResponseEntity.status(200).body(new MsgResp("User Workout Program created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update an user workout program
    @PutMapping("/{userId}/{workoutId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Integer userId, @PathVariable("workoutId") Integer workoutId, @RequestBody UserWorkoutProgramRequestResponse userWorkoutProgramRequest) {

        try {
            userWorkoutProgramService.updateUserWorkoutProgram(userId, workoutId, userWorkoutProgramRequest);
            return ResponseEntity.status(200).body(new MsgResp("User Workout Program updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete an user workout program
    @DeleteMapping("/{userId}/{workoutId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteUserRole(@PathVariable("userId") Integer userId, @PathVariable("workoutId") Integer workoutId) {

        try {
            userWorkoutProgramService.deleteUserWorkoutProgram(userId, workoutId);
            return ResponseEntity.status(200).body(new MsgResp("User Workout Program deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}