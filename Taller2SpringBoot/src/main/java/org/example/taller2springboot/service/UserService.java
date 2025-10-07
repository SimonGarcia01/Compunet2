package org.example.taller2springboot.service;

import org.example.taller2springboot.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    long getCount();

    List<User> getUsers();

    Optional<User> findById(Integer id);

    /**
     * Crea un usuario y le asigna los roles indicados (regla: >=1 rol).
     */
    User createUser(User user, List<Integer> roleIds);

    /**
     * Reemplaza los roles del usuario por los indicados (regla: >=1 rol).
     */
    User updateUserRoles(Integer userId, List<Integer> roleIds);

    /**
     * Retorna los usuarios que tienen un rol por nombre (case-insensitive).
     */
    List<User> getUsersByRoleName(String roleName);

    void deleteUser(Integer id);
}
