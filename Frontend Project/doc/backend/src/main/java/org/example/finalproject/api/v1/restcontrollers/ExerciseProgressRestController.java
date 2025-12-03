package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.ExerciseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise_progress")
public class ExerciseProgressRestController {

    // Beans
    @Autowired
    private ExerciseProgressService exerciseProgressService;

    // Get my progress (progress of authenticated user)
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Usuario') or hasAuthority('Entrenador')")
    public ResponseEntity<?> getMyProgress() {
        try {
            List<ExerciseProgressResponse> progress = exerciseProgressService.getMyProgress();
            return ResponseEntity.status(200).body(progress);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MsgResp("Error al obtener progreso: " + e.getMessage()));
        }
    }

    // Get student progress (for trainers to view their students' progress)
    @GetMapping("/student/{studentEmail}")
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Entrenador')")
    public ResponseEntity<?> getStudentProgress(@PathVariable("studentEmail") String studentEmail) {
        try {
            // Decodificar el email si está codificado en la URL (ej: %40 -> @)
            String decodedEmail = java.net.URLDecoder.decode(studentEmail, java.nio.charset.StandardCharsets.UTF_8);
            List<ExerciseProgressResponse> progress = exerciseProgressService.getStudentProgress(decodedEmail);
            return ResponseEntity.status(200).body(progress);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MsgResp("Error al obtener progreso del estudiante: " + e.getMessage()));
        }
    }

    // Get exercise progress by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getExerciseProgressById(@PathVariable("id") Integer id) {
        try {
            ExerciseProgressResponse progress = exerciseProgressService.findById(id);
            return ResponseEntity.status(200).body(progress);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }

    // Get all exercise progress
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllExerciseProgress() {
        var progress = exerciseProgressService.getAllExerciseProgress();
        return ResponseEntity.status(200).body(progress);
    }

    // Create a new exercise progress
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Usuario')")
    public ResponseEntity<?> createExerciseProgress(@RequestBody ExerciseProgressRequest exerciseProgressRequest) {
        try {
            exerciseProgressService.createExerciseProgress(exerciseProgressRequest);
            return ResponseEntity.status(200).body(new MsgResp("Exercise Progress created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    // Update an exercise progress
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Usuario')")
    public ResponseEntity<?> updateExerciseProgress(@PathVariable("id") Integer id, @RequestBody ExerciseProgressRequest exerciseProgressRequest) {
        try {
            exerciseProgressService.updateExerciseProgress(id, exerciseProgressRequest);
            return ResponseEntity.status(200).body(new MsgResp("Exercise Progress updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    // Delete an exercise progress
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Usuario')")
    public ResponseEntity<?> deleteExerciseProgress(@PathVariable("id") Integer id) {
        try {
            exerciseProgressService.deleteExerciseProgress(id);
            return ResponseEntity.status(200).body(new MsgResp("Exercise Progress deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }
}

