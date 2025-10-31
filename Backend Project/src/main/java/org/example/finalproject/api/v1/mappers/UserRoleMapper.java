package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.UserRoleRequestResponse;
import org.example.finalproject.entity.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    public UserRole toUserRole(UserRoleRequestResponse userRoleRequest);
    public UserRoleRequestResponse toUserRoleResponse(UserRole userRole);

}
