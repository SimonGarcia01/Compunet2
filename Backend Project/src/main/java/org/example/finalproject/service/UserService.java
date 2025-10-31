package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.UserRequest;
import org.example.finalproject.api.v1.dtos.UserResponse;
import org.example.finalproject.entity.User;
import org.example.finalproject.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    long getCount();

    List<UserResponse> getAllUsers();

    UserResponse findById(Integer id) throws ResourceNotFoundException;

    User createUser(User user, List<Integer> roleIds);

    void createUser(UserRequest userRequest);

    void updateUser(Integer id, UserRequest userRequest);

    User updateUserRoles(Integer userId, List<Integer> roleIds);

    List<User> getUsersByRoleName(String roleName);

    void deleteUser(Integer id);

    // Puedes mantener esta versión que lanza excepción...
    User findByEmail(String email);

    //y también la segura en Optional:
    Optional<User> findByEmailOpt(String email);

    boolean existsByEmail(String email);

    // 👇 NUEVO: para el CustomUserDetailService
    Optional<User> findByEmailWithAuthorities(String email);
}