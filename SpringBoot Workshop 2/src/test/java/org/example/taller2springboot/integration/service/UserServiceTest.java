package org.example.taller2springboot.integration.service;

import org.example.taller2springboot.entity.Role;
import org.example.taller2springboot.entity.User;
import org.example.taller2springboot.entity.UserRole;
import org.example.taller2springboot.entity.UserRoleId;
import org.example.taller2springboot.repository.RoleRepository;
import org.example.taller2springboot.repository.UserRepository;
import org.example.taller2springboot.repository.UserRoleRepository;
import org.example.taller2springboot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getCount_shouldDelegateToRepository() {
        when(userRepository.count()).thenReturn(5L);
        assertEquals(5L, userService.getCount());
    }

    @Test
    void getUsers_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        assertEquals(2, userService.getUsers().size());
    }

    @Test
    void findById_whenPresent_shouldReturnOptionalUser() {
        User u = new User(); u.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(u));

        Optional<User> out = userService.findById(1);
        assertTrue(out.isPresent());
        assertEquals(1, out.get().getUserId());
    }

    @Test
    void createUser_whenNoRoles_shouldThrow() {
        User u = new User();
        assertThrows(RuntimeException.class, () -> userService.createUser(u, Collections.emptyList()));
        assertThrows(RuntimeException.class, () -> userService.createUser(u, null));
        verifyNoInteractions(roleRepository, userRoleRepository);
    }

    @Test
    void createUser_whenRolesProvided_shouldSaveUserAndLinks() {
        User toSave = new User();
        User saved = new User(); saved.setUserId(100);

        Role r1 = new Role(); r1.setRoleId(1);
        Role r2 = new Role(); r2.setRoleId(2);

        when(userRepository.save(any(User.class))).thenReturn(saved);
        when(roleRepository.findById(1)).thenReturn(Optional.of(r1));
        when(roleRepository.findById(2)).thenReturn(Optional.of(r2));
        when(userRepository.getReferenceById(100)).thenReturn(saved);

        User result = userService.createUser(toSave, List.of(1, 2));

        assertNotNull(result);
        assertEquals(100, result.getUserId());
        // Debe crear 2 enlaces UserRole
        verify(userRoleRepository, times(2)).save(any(UserRole.class));
    }

    @Test
    void updateUserRoles_whenUserNotFound_shouldThrow() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.updateUserRoles(999, List.of(1)));
    }

    @Test
    void updateUserRoles_whenEmptyRoles_shouldThrow() {
        User existing = new User(); existing.setUserId(7);
        when(userRepository.findById(7)).thenReturn(Optional.of(existing));
        assertThrows(RuntimeException.class, () -> userService.updateUserRoles(7, Collections.emptyList()));
    }

    @Test
    void updateUserRoles_whenValid_shouldDeleteOldAndInsertNew() {
        User existing = new User(); existing.setUserId(7);
        Role r1 = new Role(); r1.setRoleId(1);

        when(userRepository.findById(7)).thenReturn(Optional.of(existing));
        when(roleRepository.findById(1)).thenReturn(Optional.of(r1));
        when(userRepository.getReferenceById(7)).thenReturn(existing);

        User out = userService.updateUserRoles(7, List.of(1));

        assertNotNull(out);
        verify(userRoleRepository).deleteUserRoleById_UserId(7);
        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void getUsersByRoleName_whenRoleExists_shouldReturnUsers() {
        // role encontrado
        Role admin = new Role(); admin.setRoleId(1); admin.setName("ADMIN");
        when(roleRepository.findByNameIgnoreCase("ADMIN")).thenReturn(Optional.of(admin));

        // enlaces UserRole (uno con role=1 y otro con otro role)
        UserRole ur1 = new UserRole();
        ur1.setId(new UserRoleId()); ur1.getId().setUserId(100); ur1.getId().setRoleId(1);
        UserRole ur2 = new UserRole();
        ur2.setId(new UserRoleId()); ur2.getId().setUserId(200); ur2.getId().setRoleId(2);
        when(userRoleRepository.findAll()).thenReturn(List.of(ur1, ur2));

        // users
        User u100 = new User(); u100.setUserId(100);
        when(userRepository.findById(100)).thenReturn(Optional.of(u100));

        List<User> users = userService.getUsersByRoleName("ADMIN");
        assertEquals(1, users.size());
        assertEquals(100, users.get(0).getUserId());
    }

    @Test
    void deleteUser_shouldDelegate() {
        userService.deleteUser(9);
        verify(userRepository).deleteById(9);
    }
}
