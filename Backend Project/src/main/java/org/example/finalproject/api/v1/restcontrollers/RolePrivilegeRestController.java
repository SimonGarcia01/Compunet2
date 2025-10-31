package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.entity.RolePrivilegeId;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.NotificationService;
import org.example.finalproject.service.RolePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/role_privileges")
public class RolePrivilegeRestController {

    // Beans
    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    // Get role privilege by ID
    @GetMapping("/{roleId}/{privilegeId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getRolePrivilegeById(@PathVariable("roleId") Integer roleId, @PathVariable("privilegeId") Integer privilegeId) {

        try {
            RolePrivilegeRequestResponse rolePrivilege = rolePrivilegeService.findById(roleId, privilegeId);
            return ResponseEntity.status(200).body(rolePrivilege);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all role privileges
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllRolePrivileges() {

        var rolePrivileges = rolePrivilegeService.getAllRolePrivileges();
        return ResponseEntity.status(200).body(rolePrivileges);

    }

    // Create a new role privilege
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createRolePrivilege(@RequestBody RolePrivilegeRequestResponse rolePrivilegeRequest) {

        try {
            rolePrivilegeService.createRolePrivilege(rolePrivilegeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Role Privilege created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a role privilege
    @PutMapping("/{roleId}/{privilegeId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateRolePrivilege(@PathVariable("roleId") Integer roleId, @PathVariable("privilegeId") Integer privilegeId, @RequestBody RolePrivilegeRequestResponse rolePrivilegeRequest) {

        try {
            rolePrivilegeService.updateRolePrivilege(roleId, privilegeId, rolePrivilegeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Role Privilege updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a role privilege
    @DeleteMapping("/{roleId}/{privilegeId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteRolePrivilege(@PathVariable("roleId") Integer roleId, @PathVariable("privilegeId") Integer privilegeId) {

        try {
            rolePrivilegeService.deleteRolePrivilege(roleId, privilegeId);
            return ResponseEntity.status(200).body(new MsgResp("Role Privilege deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}