package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.RoleRequest;
import org.example.finalproject.api.v1.dtos.RoleResponse;
import org.example.finalproject.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    public Role toRole(RoleRequest roleRequest);
    public RoleResponse toRoleResponse(Role role);

}
