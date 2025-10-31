package org.example.taller3mvc.service;

import org.example.taller3mvc.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    long getCount();

    List<User> getUsers();

    Optional<User> findById(Integer id);

    User createUser(User user, List<Integer> roleIds);

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