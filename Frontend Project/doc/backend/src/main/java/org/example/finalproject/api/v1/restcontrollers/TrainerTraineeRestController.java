package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.TrainerTraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/trainers_trainees")
public class TrainerTraineeRestController {

    // Beans
    @Autowired
    private TrainerTraineeService trainerTraineeService;

    // Get trainer trainee by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getTrainerTraineeById(@PathVariable("id") Integer id) {

        try {
            TrainerTraineeResponse trainerTrainee = trainerTraineeService.findById(id);
            return ResponseEntity.status(200).body(trainerTrainee);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all trainer trainees
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllTrainerTrainees() {

        var trainerTrainees = trainerTraineeService.getAllTrainerTrainees();
        return ResponseEntity.status(200).body(trainerTrainees);

    }

    // Create a new trainer trainee
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createTrainerTrainee(@RequestBody TrainerTraineeRequest trainerTraineeRequest) {

        try {
            trainerTraineeService.createTrainerTrainee(trainerTraineeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Trainer Trainee created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a trainer trainee
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateTrainerTrainee(@PathVariable("id") Integer id, @RequestBody TrainerTraineeRequest trainerTraineeRequest) {

        try {
            trainerTraineeService.updateTrainerTrainee(id, trainerTraineeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Trainer Trainee updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a trainer trainee
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteTrainerTrainee(@PathVariable("id") Integer id) {

        try {
            trainerTraineeService.deleteTrainerTrainee(id);
            return ResponseEntity.status(200).body(new MsgResp("Trainer Trainee deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}