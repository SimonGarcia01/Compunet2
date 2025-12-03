package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/privileges")
public class PrivilegeRestController {

    // Beans
    @Autowired
    private PrivilegeService privilegeService;

    // Get privilege by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getPrivilegeById(@PathVariable("id") Integer id) {

        try {
            PrivilegeResponse privilege = privilegeService.findById(id);
            return ResponseEntity.status(200).body(privilege);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all privileges
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllPrivileges() {

        var privileges = privilegeService.getAllPrivileges();
        return ResponseEntity.status(200).body(privileges);

    }

    // Create a new privilege
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createPrivilege(@RequestBody PrivilegeRequest privilegeRequest) {

        try {
            privilegeService.createPrivilege(privilegeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Privilege created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a privilege
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updatePrivilege(@PathVariable("id") Integer id, @RequestBody PrivilegeRequest privilegeRequest) {

        try {
            privilegeService.updatePrivilege(id, privilegeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Privilege updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a privilege
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deletePrivilege(@PathVariable("id") Integer id) {

        try {
            privilegeService.deletePrivilege(id);
            return ResponseEntity.status(200).body(new MsgResp("Privilege deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}