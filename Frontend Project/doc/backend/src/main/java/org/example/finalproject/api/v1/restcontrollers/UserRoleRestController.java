package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.entity.UserRoleId;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.TrainerTraineeService;
import org.example.finalproject.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user_roles")
public class UserRoleRestController {

    // Beans
    @Autowired
    private UserRoleService userRoleService;

    // Get user role by ID
    @GetMapping("/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getUserRoleById(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {

        try {
            UserRoleRequestResponse userRole = userRoleService.findById(userId, roleId);
            return ResponseEntity.status(200).body(userRole);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all user roles
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllUserRoles() {

        var userRoles = userRoleService.getAllUserRoles();
        return ResponseEntity.status(200).body(userRoles);

    }

    // Create a new user role
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleRequestResponse userRoleRequest) {

        try {
            userRoleService.createUserRole(userRoleRequest);
            return ResponseEntity.status(200).body(new MsgResp("User Role created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update an user role
    @PutMapping("/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId, @RequestBody UserRoleRequestResponse userRoleRequest) {

        try {
            userRoleService.updateUserRole(userId, roleId, userRoleRequest);
            return ResponseEntity.status(200).body(new MsgResp("User Role updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete an user role
    @DeleteMapping("/{userId}/{roleId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteUserRole(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {

        try {
            userRoleService.deleteUserRole(userId, roleId);
            return ResponseEntity.status(200).body(new MsgResp("User Role deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}