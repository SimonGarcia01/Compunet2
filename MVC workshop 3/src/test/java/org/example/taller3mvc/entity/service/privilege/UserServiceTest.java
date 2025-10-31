package org.example.taller3mvc.entity.service.privilege;

import org.example.taller3mvc.entity.Role;
import org.example.taller3mvc.entity.User;
import org.example.taller3mvc.service.PrivilegeService;
import org.example.taller3mvc.service.RoleService;
import org.example.taller3mvc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private PrivilegeService privilegeService;
    @Autowired private PasswordEncoder encoder;

    private Integer roleId;

    @BeforeEach
    void setUp() {
        var pv = privilegeService.createPrivilege(new org.example.taller3mvc.entity.Privilege(null,"PV",""));
        Role r = new Role();
        r.setName("Administrador");
        roleId = roleService.createRole(r, List.of(pv.getPrivilegeId())).getRoleId();
    }

    private User demoUser(String email) {
        User u = new User();
        u.setEmail(email);
        u.setPersonalId("123");
        u.setName("Juan");
        u.setEncryptedPassword(encoder.encode("12345678"));
        u.setActive(true);
        u.setCreationDate(LocalDate.now());
        return u;
    }

    @Test
    void createUser_y_asignarRoles() {
        User saved = userService.createUser(demoUser("a@u.icesi.edu.co"), List.of(roleId));
        assertNotNull(saved.getUserId());
        assertTrue(userService.existsByEmail("a@u.icesi.edu.co"));
    }

    @Test
    void updateUserRoles_reemplaza() {
        User saved = userService.createUser(demoUser("b@u.icesi.edu.co"), List.of(roleId));
        // Crear segundo rol
        var pv2 = privilegeService.createPrivilege(new org.example.taller3mvc.entity.Privilege(null,"PV2",""));
        Role r2 = new Role(); r2.setName("Editor");
        Integer role2Id = roleService.createRole(r2, List.of(pv2.getPrivilegeId())).getRoleId();

        userService.updateUserRoles(saved.getUserId(), List.of(role2Id));
        assertTrue(userService.getUsersByRoleName("Editor").stream().anyMatch(u -> u.getUserId().equals(saved.getUserId())));
    }
}