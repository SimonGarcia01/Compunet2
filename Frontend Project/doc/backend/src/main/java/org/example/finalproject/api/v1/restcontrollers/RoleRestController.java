package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/roles")
public class RoleRestController {

    // Beans
    @Autowired
    private RoleService roleService;

    // Get role by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getRoleById(@PathVariable("id") Integer id) {

        try {
            RoleResponse role = roleService.findById(id);
            return ResponseEntity.status(200).body(role);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all roles
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllRoles() {

        var exercises = roleService.getAllRoles();
        return ResponseEntity.status(200).body(exercises);

    }

    // Create a new role
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createRole(@RequestBody RoleRequest roleRequest) {

        try {
            roleService.createRole(roleRequest);
            return ResponseEntity.status(200).body(new MsgResp("Role created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a role
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateRole(@PathVariable("id") Integer id, @RequestBody RoleRequest roleRequest) {

        try {
            roleService.updateRole(id, roleRequest);
            return ResponseEntity.status(200).body(new MsgResp("Role updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a role
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Integer id) {

        try {
            roleService.deleteRole(id);
            return ResponseEntity.status(200).body(new MsgResp("Role deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}