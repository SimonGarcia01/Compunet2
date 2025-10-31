package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.api.v1.dtos.UserRequest;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    //Get user by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id) {
        try{
            var user = userService.findById(id);
            return ResponseEntity.status(200).body(user);
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }

    //Get all users
    @GetMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.status(200).body(users);
    }

    //Create a user
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try{
            userService.createUser(userRequest);
            return ResponseEntity.status(200).body(new MsgResp("User created successfully."));
        }catch(RuntimeException e){
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }


    //Update a user
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody UserRequest userRequest) {
        try{
            userService.updateUser(id, userRequest);
            return ResponseEntity.status(200).body(new MsgResp("User updated successfully"));
        }catch(RuntimeException e){
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    //Delete a user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Integer id) {
        try{
            userService.deleteUser(id);
            return ResponseEntity.status(200).body(new MsgResp("User deleted successfully"));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }


    }

}
