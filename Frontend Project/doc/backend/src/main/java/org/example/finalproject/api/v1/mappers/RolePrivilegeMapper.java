package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.RolePrivilegeRequestResponse;
import org.example.finalproject.api.v1.dtos.RoleRequest;
import org.example.finalproject.api.v1.dtos.RoleResponse;
import org.example.finalproject.entity.Role;
import org.example.finalproject.entity.RolePrivilege;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePrivilegeMapper {

    public RolePrivilege toRolePrivilege(RolePrivilegeRequestResponse rolePrivilegeRequest);
    public RolePrivilegeRequestResponse toRolePrivilegeResponse(RolePrivilege rolePrivilege);

}
