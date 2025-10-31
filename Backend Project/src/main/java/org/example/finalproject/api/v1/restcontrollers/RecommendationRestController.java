package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationRestController {

    // Beans
    @Autowired
    private RecommendationService recommendationService;

    // Get recommendation by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getRecommendationById(@PathVariable("id") Integer id) {

        try {
            RecommendationResponse recommendation = recommendationService.findById(id);
            return ResponseEntity.status(200).body(recommendation);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all recommendations
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllRecommendations() {

        var exercises = recommendationService.getAllRecommendations();
        return ResponseEntity.status(200).body(exercises);

    }

    // Create a new recommendation
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createRecommendation(@RequestBody RecommendationRequest recommendationRequest) {

        try {
            recommendationService.createRecommendation(recommendationRequest);
            return ResponseEntity.status(200).body(new MsgResp("Recommendation created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a recommendation
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateRecommendation(@PathVariable("id") Integer id, @RequestBody RecommendationRequest recommendationRequest) {

        try {
            recommendationService.updateRecommendation(id, recommendationRequest);
            return ResponseEntity.status(200).body(new MsgResp("Recommendation updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a recommendation
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteRecommendation(@PathVariable("id") Integer id) {

        try {
            recommendationService.deleteRecommendation(id);
            return ResponseEntity.status(200).body(new MsgResp("Recommendation deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}