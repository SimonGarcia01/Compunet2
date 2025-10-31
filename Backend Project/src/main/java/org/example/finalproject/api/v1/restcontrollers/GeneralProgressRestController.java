package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.GeneralProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/general_progress")
public class GeneralProgressRestController {

    // Beans
    @Autowired
    private GeneralProgressService generalProgressService;

    // Get general progress by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getGeneralProgressById(@PathVariable("id") Integer id) {

        try {
            GeneralProgressResponse generalProgress = generalProgressService.findById(id);
            return ResponseEntity.status(200).body(generalProgress);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all general progress
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllGeneralProgress() {

        var generalProgress = generalProgressService.getAllGeneralProgress();
        return ResponseEntity.status(200).body(generalProgress);

    }

    // Create a new general progress
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createGeneralProgress(@RequestBody GeneralProgressRequest generalProgressRequest) {

        try {
            generalProgressService.createGeneralProgress(generalProgressRequest);
            return ResponseEntity.status(200).body(new MsgResp("General Progress created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a general progress
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateGeneralProgress(@PathVariable("id") Integer id, @RequestBody GeneralProgressRequest generalProgressRequest) {

        try {
            generalProgressService.updateGeneralProgress(id, generalProgressRequest);
            return ResponseEntity.status(200).body(new MsgResp("General Progress updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a general progress
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteGeneralProgress(@PathVariable("id") Integer id) {

        try {
            generalProgressService.deleteGeneralProgress(id);
            return ResponseEntity.status(200).body(new MsgResp("General Progress deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}