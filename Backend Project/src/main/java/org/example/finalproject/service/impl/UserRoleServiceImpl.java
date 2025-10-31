package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.UserRoleRequestResponse;
import org.example.finalproject.api.v1.mappers.RoleMapper;
import org.example.finalproject.api.v1.mappers.UserMapper;
import org.example.finalproject.api.v1.mappers.UserRoleMapper;
import org.example.finalproject.entity.RolePrivilegeId;
import org.example.finalproject.entity.UserRole;
import org.example.finalproject.entity.UserRoleId;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.UserRoleRepository;
import org.example.finalproject.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    // Beans
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;


    // Find user role by ID
    @Override
    public UserRoleRequestResponse findById(Integer userId, Integer roleId) {

        //Make the id so then it can be used to look for it
        UserRoleId id = new UserRoleId();
        id.setUserId(userId);
        id.setRoleId(roleId);

        return userRoleRepository.findById(id)
                .map(userRoleMapper::toUserRoleResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + id));
    }

    // Get all user roles
    @Override
    public List<UserRoleRequestResponse> getAllUserRoles() {

        return userRoleRepository.findAll().stream()
                .map(userRoleMapper::toUserRoleResponse)
                .toList();

    }

    // Create a new user role
    @Override
    @Transactional
    public void createUserRole(UserRoleRequestResponse request) {

        if (request.getUser() == null || request.getRole() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        UserRole userRole = userRoleMapper.toUserRole(request);

        userRoleRepository.save(userRole);

    }

    // Update an existing user role
    @Override
    @Transactional
    public void updateUserRole(Integer userId, Integer roleId, UserRoleRequestResponse request) {

        //Make the id so then it can be used to look for it
        UserRoleId id = new UserRoleId();
        id.setUserId(userId);
        id.setRoleId(roleId);

        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + id));

        if (request.getUser() != null) {
            userRole.setUser(userMapper.toUser(request.getUser()));
        }

        if (request.getRole() != null) {
            userRole.setRole(roleMapper.toRole(request.getRole()));
        }

        userRoleRepository.save(userRole);

    }

    // Delete a use role
    @Override
    public void deleteUserRole(Integer userId, Integer roleId) {

        //Make the id so then it can be used to look for it
        UserRoleId id = new UserRoleId();
        id.setUserId(userId);
        id.setRoleId(roleId);

        userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + id));

        userRoleRepository.deleteById(id);

    }
}
