package org.example.taller3mvc.entity.service.privilege;

import org.example.taller3mvc.entity.Privilege;
import org.example.taller3mvc.entity.Role;
import org.example.taller3mvc.service.PrivilegeService;
import org.example.taller3mvc.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleServiceTest {

    @Autowired private RoleService roleService;
    @Autowired private PrivilegeService privilegeService;

    private Integer p1Id, p2Id;

    @BeforeEach
    void seedPrivileges() {
        p1Id = privilegeService.createPrivilege(new Privilege(null,"P1","")).getPrivilegeId();
        p2Id = privilegeService.createPrivilege(new Privilege(null,"P2","")).getPrivilegeId();
    }

    @Test
    void createRole_con_privilegios() {
        Role r = new Role();
        r.setName("Administrador");
        Role saved = roleService.createRole(r, List.of(p1Id, p2Id));

        assertNotNull(saved.getRoleId());
        assertEquals("Administrador", saved.getName());
    }

    @Test
    void updateRolePrivileges_reemplaza() {
        Role r = new Role();
        r.setName("Editor");
        r = roleService.createRole(r, List.of(p1Id));

        Role after = roleService.updateRolePrivileges(r.getRoleId(), List.of(p2Id));
        assertNotNull(after);
    }
}