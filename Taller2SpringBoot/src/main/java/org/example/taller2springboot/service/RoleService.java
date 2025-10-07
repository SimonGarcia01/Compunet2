package org.example.taller2springboot.service;

import org.example.taller2springboot.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    long getCount();

    List<Role> getRoles();

    Optional<Role> findById(Integer id);

    Optional<Role> findByName(String name);

    /**
     * Crea un rol con los privilegios indicados (regla: >=1 privilegio).
     */
    Role createRole(Role role, List<Integer> privilegeIds);

    /**
     * Reemplaza los privilegios del rol por los indicados (regla: >=1 privilegio).
     */
    Role updateRolePrivileges(Integer roleId, List<Integer> privilegeIds);

    void deleteRole(Integer id);
}
