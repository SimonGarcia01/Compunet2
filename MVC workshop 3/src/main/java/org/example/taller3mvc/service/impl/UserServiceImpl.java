package org.example.taller3mvc.service.impl;

import org.example.taller3mvc.entity.Role;
import org.example.taller3mvc.entity.User;
import org.example.taller3mvc.entity.UserRole;
import org.example.taller3mvc.entity.UserRoleId;
import org.example.taller3mvc.repository.RoleRepository;
import org.example.taller3mvc.repository.UserRepository;
import org.example.taller3mvc.repository.UserRoleRepository;
import org.example.taller3mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;

    @Override
    public long getCount() { return userRepository.count(); }

    @Override
    public List<User> getUsers() { return userRepository.findAll(); }

    @Override
    public Optional<User> findById(Integer id) { return userRepository.findById(id); }

    @Override
    @Transactional
    public User createUser(User user, List<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new RuntimeException("User must have at least one role");
        }
        User saved = userRepository.save(user);
        attachRoles(saved.getUserId(), roleIds);
        return saved;
    }

    @Override
    @Transactional
    public User updateUserRoles(Integer userId, List<Integer> roleIds) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        if (roleIds == null || roleIds.isEmpty()) {
            throw new RuntimeException("User must have at least one role");
        }

        userRoleRepository.deleteUserRoleById_UserId(userId);
        attachRoles(userId, roleIds);
        return userRepository.getReferenceById(userId);
    }

    @Override
    public List<User> getUsersByRoleName(String roleName) {
        Role role = roleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() -> new RuntimeException("Role with name '" + roleName + "' not found"));

        var links = userRoleRepository.findAll();
        List<User> result = new ArrayList<>();
        for (UserRole ur : links) {
            if (Objects.equals(ur.getId().getRoleId(), role.getRoleId())) {
                userRepository.findById(ur.getId().getUserId()).ifPresent(result::add);
            }
        }
        return result;
    }

    @Override
    public void deleteUser(Integer id) { userRepository.deleteById(id); }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Override
    public Optional<User> findByEmailOpt(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email); // <-- FIX
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmailWithAuthorities(String email) {
        // Usa el que prefieras:
        // return userRepository.findOneWithRolesAndPrivilegesByEmailIgnoreCase(email);
        return userRepository.findOneFetchByEmail(email);
    }

    private void attachRoles(Integer userId, List<Integer> roleIds) {
        User userRef = userRepository.getReferenceById(userId);

        for (Integer roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role with id " + roleId + " not found"));

            UserRoleId id = new UserRoleId();
            id.setUserId(userId);
            id.setRoleId(role.getRoleId());

            UserRole link = new UserRole();
            link.setId(id);
            link.setUser(userRef);      // @MapsId si aplica
            link.setRole(role);         // @MapsId si aplica
            link.setAssignedDate(java.time.LocalDate.now()); // evita null

            userRoleRepository.save(link);
        }
    }
}