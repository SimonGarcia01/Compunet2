package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.HistoricalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/historical_records")
public class HistoricalRecordRestController {

    // Beans
    @Autowired
    private HistoricalRecordService historicalRecordService;

    // Get historical record by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getHistoricalRecordById(@PathVariable("id") Integer id) {

        try {
            HistoricalRecordResponse historicalRecord = historicalRecordService.findById(id);
            return ResponseEntity.status(200).body(historicalRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all historical records
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllHistoricalRecords() {

        var historicalRecords = historicalRecordService.getAllHistoricalRecords();
        return ResponseEntity.status(200).body(historicalRecords);

    }

    // Create a new historical record
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createHistoricalRecord(@RequestBody HistoricalRecordRequest historicalRecordRequest) {

        try {
            historicalRecordService.createHistoricalRecord(historicalRecordRequest);
            return ResponseEntity.status(200).body(new MsgResp("Historical Record created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a historical record
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateHistoricalRecord(@PathVariable("id") Integer id, @RequestBody HistoricalRecordRequest historicalRecordRequest) {

        try {
            historicalRecordService.updateHistoricalRecord(id, historicalRecordRequest);
            return ResponseEntity.status(200).body(new MsgResp("Historical Record updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a historical record
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteHistoricalRecord(@PathVariable("id") Integer id) {

        try {
            historicalRecordService.deleteHistoricalRecord(id);
            return ResponseEntity.status(200).body(new MsgResp("Historical Record deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}