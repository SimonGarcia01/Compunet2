package org.example.taller2springboot.service.impl;

import org.example.taller2springboot.entity.Role;
import org.example.taller2springboot.entity.User;
import org.example.taller2springboot.entity.UserRole;
import org.example.taller2springboot.entity.UserRoleId;
import org.example.taller2springboot.repository.RoleRepository;
import org.example.taller2springboot.repository.UserRepository;
import org.example.taller2springboot.repository.UserRoleRepository;
import org.example.taller2springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public long getCount() {
        return userRepository.count();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User createUser(User user, List<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new RuntimeException("User must have at least one role");
        }
        // Persistimos el usuario primero
        User saved = userRepository.save(user);

        // Asignamos roles vía tabla puente UserRole
        attachRoles(saved.getUserId(), roleIds);

        return saved;
    }

    @Override
    @Transactional
    public User updateUserRoles(Integer userId, List<Integer> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        if (roleIds == null || roleIds.isEmpty()) {
            throw new RuntimeException("User must have at least one role");
        }

        // Limpiar roles actuales (delete en tabla puente)
        userRoleRepository.deleteUserRoleById_UserId(userId);

        // Re-asignar
        attachRoles(userId, roleIds);

        return user;
    }

    @Override
    public List<User> getUsersByRoleName(String roleName) {
        // Buscamos el rol por nombre y luego traemos los UserRole por roleId
        Role role = roleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() -> new RuntimeException("Role with name '" + roleName + "' not found"));

        // Obtenemos las relaciones y resolvemos usuarios
        var links = userRoleRepository.findAll(); // simple; para optimizar, crea un método por roleId
        List<User> result = new ArrayList<>();
        for (UserRole ur : links) {
            if (Objects.equals(ur.getId().getRoleId(), role.getRoleId())) {
                userRepository.findById(ur.getId().getUserId()).ifPresent(result::add);
            }
        }
        return result;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    private void attachRoles(Integer userId, List<Integer> roleIds) {
        for (Integer roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role with id " + roleId + " not found"));

            UserRoleId id = new UserRoleId();
            id.setUserId(userId);
            id.setRoleId(role.getRoleId());

            UserRole link = new UserRole();
            link.setId(id);
            // Si tu entidad tiene @MapsId, setea también las refs:
            link.setUser(userRepository.getReferenceById(userId));
            link.setRole(role);

            userRoleRepository.save(link);
        }
    }
}
