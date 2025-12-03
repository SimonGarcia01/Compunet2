package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.RolePrivilegeRequestResponse;


import java.util.List;

public interface RolePrivilegeService {

    RolePrivilegeRequestResponse findById(Integer roleId, Integer privilegeId);
    List<RolePrivilegeRequestResponse> getAllRolePrivileges();
    void createRolePrivilege(RolePrivilegeRequestResponse rolePrivilegeRequest);
    void updateRolePrivilege(Integer roleId, Integer privilegeId, RolePrivilegeRequestResponse rolePrivilegeRequest);
    void deleteRolePrivilege(Integer roleId, Integer privilegeId);

}
