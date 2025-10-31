package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.AvailableSpaceRequest;
import org.example.finalproject.api.v1.dtos.AvailableSpaceResponse;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.AvailableSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/available_spaces")
public class AvailableSpaceRestController {

    @Autowired
    private AvailableSpaceService availableSpaceService;

    // Get available space by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAvailableSpaceById(@PathVariable("id") Integer id) {
        try {
            AvailableSpaceResponse space = availableSpaceService.findById(id);
            return ResponseEntity.status(200).body(space);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }

    // Get all available spaces
    @GetMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllAvailableSpaces() {
        var spaces = availableSpaceService.getAllAvailableSpaces();
        return ResponseEntity.status(200).body(spaces);
    }

    // Create a new available space
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createAvailableSpace(@RequestBody AvailableSpaceRequest availableSpaceRequest) {
        try {
            availableSpaceService.createAvailableSpace(availableSpaceRequest);
            return ResponseEntity.status(200).body(new MsgResp("Available space created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    // Update an available space
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateAvailableSpace(@PathVariable("id") Integer id,
                                                  @RequestBody AvailableSpaceRequest availableSpaceRequest) {
        try {
            availableSpaceService.updateAvailableSpace(id, availableSpaceRequest);
            return ResponseEntity.status(200).body(new MsgResp("Available space updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    // Delete an available space
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteAvailableSpace(@PathVariable("id") Integer id) {
        try {
            availableSpaceService.deleteAvailableSpace(id);
            return ResponseEntity.status(200).body(new MsgResp("Available space deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }
}
