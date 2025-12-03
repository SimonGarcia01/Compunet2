package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.MessageRequest;
import org.example.finalproject.api.v1.dtos.MessageResponse;
import org.example.finalproject.api.v1.dtos.UserRoleRequestResponse;
import org.example.finalproject.entity.UserRoleId;

import java.util.List;

public interface UserRoleService {

    UserRoleRequestResponse findById(Integer userId, Integer roleId);
    List<UserRoleRequestResponse> getAllUserRoles();
    void createUserRole(UserRoleRequestResponse request);
    void updateUserRole(Integer userId, Integer roleId, UserRoleRequestResponse request);
    void deleteUserRole(Integer userId, Integer roleId);

}
