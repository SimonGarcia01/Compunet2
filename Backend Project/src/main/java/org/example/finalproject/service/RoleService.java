package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.NotificationRequest;
import org.example.finalproject.api.v1.dtos.PrivilegeRequest;
import org.example.finalproject.api.v1.dtos.RoleRequest;
import org.example.finalproject.api.v1.dtos.RoleResponse;
import org.example.finalproject.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    long getCount();

    List<RoleResponse> getAllRoles();

    RoleResponse findById(Integer id);

    Optional<Role> findByName(String name);

    /**
     * Crea un rol con los privilegios indicados (regla: >=1 privilegio).
     */
    void createRole(Role role, List<Integer> privilegeIds);

    /**
     * Reemplaza los privilegios del rol por los indicados (regla: >=1 privilegio).
     */
    void updateRolePrivileges(Integer roleId, List<Integer> privilegeIds);

    void deleteRole(Integer id);

    void createRole(RoleRequest roleRequest);
    void updateRole(Integer id, RoleRequest roleRequest);  // <-- nuevo

}
