package org.example.taller3mvc.entity.service.privilege;

import org.example.taller3mvc.entity.Privilege;
import org.example.taller3mvc.service.PrivilegeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PrivilegeServiceTest {

    @Autowired
    private PrivilegeService privilegeService;

    @Test
    void createPrivilege_ok() {
        Privilege p = new Privilege();
        p.setName("VER_EVENTOS");
        p.setDescription("Puede ver eventos");
        Privilege saved = privilegeService.createPrivilege(p);

        assertNotNull(saved.getPrivilegeId());
        assertEquals("VER_EVENTOS", saved.getName());
    }

    @Test
    void createPrivilege_falla_nombre_vacio() {
        Privilege p = new Privilege();
        assertThrows(IllegalArgumentException.class, () -> privilegeService.createPrivilege(p));
    }

    @Test
    void updatePrivilege_ok() {
        Privilege p = new Privilege();
        p.setName("GESTIONAR_USUARIOS");
        p = privilegeService.createPrivilege(p);

        Privilege changes = new Privilege();
        changes.setDescription("Administra usuarios");
        Privilege updated = privilegeService.updatePrivilege(p.getPrivilegeId(), changes);

        assertEquals("Administra usuarios", updated.getDescription());
    }
}