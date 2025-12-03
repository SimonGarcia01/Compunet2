package org.example.finalproject.service.impl;

import org.example.finalproject.api.v1.dtos.UserRequest;
import org.example.finalproject.api.v1.dtos.UserResponse;
import org.example.finalproject.api.v1.mappers.UserMapper;
import org.example.finalproject.entity.Role;
import org.example.finalproject.entity.User;
import org.example.finalproject.entity.UserRole;
import org.example.finalproject.entity.UserRoleId;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.RoleRepository;
import org.example.finalproject.repository.UserRepository;
import org.example.finalproject.repository.UserRoleRepository;
import org.example.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(
                userMapper::toUserResponse
        ).toList();
    }

    @Override
    public UserResponse findById(Integer id) throws ResourceNotFoundException {
        return userRepository.findById(id).map(
                userMapper::toUserResponse
        ).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void createUser(UserRequest userRequest) {
        User user = new User();

        if(userRequest.getEmail()==null ||
                userRequest.getName()==null ||
                userRequest.getPersonalId()==null ||
                userRequest.getEncryptedPassword()==null ||
                userRequest.getPhotoUrl()==null)
            throw new MissingInfoException("One or more fields were not filled. Try again.");

        if(userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new UniquenessViolationException("Email already exists, use another one.");

        if(userRepository.findByPersonalId(userRequest.getPersonalId()).isPresent())
            throw new UniquenessViolationException("Personal ID already exists, you must use another one.");

        user.setEmail(userRequest.getEmail());
        user.setPersonalId(userRequest.getPersonalId());
        user.setName(userRequest.getName());
        user.setEncryptedPassword(userRequest.getEncryptedPassword());
        user.setPhotoUrl(userRequest.getPhotoUrl());

        //Default values:
        user.setActive(true);
        user.setCreationDate(LocalDate.now());

        // That's enough information to save the new user
        User savedUser = userRepository.save(user);

        // Attach default "Usuario" role
        Role defaultRole = roleRepository.findByNameIgnoreCase("Usuario")
                .orElseThrow(() -> new ResourceNotFoundException("Default role 'Usuario' not found"));

        UserRole link = new UserRole();
        UserRoleId linkId = new UserRoleId(savedUser.getUserId(), defaultRole.getRoleId());

        link.setId(linkId);
        link.setUser(savedUser);
        link.setRole(defaultRole);
        link.setAssignedDate(LocalDate.now());

        userRoleRepository.save(link);
    }

    @Transactional
    @Override
    public void updateUser(Integer id, UserRequest userRequest) throws ResourceNotFoundException {
        //First check if that ID actually exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update only changed fields
        //Check if the changed email doesn't already exist
        if (userRequest.getEmail() != null && userRepository.findByEmail(userRequest.getEmail()).isEmpty()){
            user.setEmail(userRequest.getEmail());
        } else {
            throw new UniquenessViolationException("The entered email address already exists.");
        }

        if(userRequest.getPersonalId() != null && userRepository.findByPersonalId(userRequest.getPersonalId()).isEmpty()){
            user.setPersonalId(userRequest.getPersonalId());
        } else {
            throw new UniquenessViolationException("The entered personal ID already exists.");
        }

        if (userRequest.getName() != null)
            user.setName(userRequest.getName());

        if (userRequest.getEncryptedPassword() != null)
            user.setEncryptedPassword(passwordEncoder.encode(userRequest.getEncryptedPassword()));

        if(userRequest.getPhotoUrl() != null)
            user.setPhotoUrl(userRequest.getPhotoUrl());

        //Always sets it to false so I can't do it like that
//        if(!userRequest.isActive())
//            user.setActive(false);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.deleteById(id);
    }

    @Override
    public long getCount() { return userRepository.count(); }

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