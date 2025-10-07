package org.example.taller2springboot.integration.service;

import org.example.taller2springboot.entity.Privilege;
import org.example.taller2springboot.entity.Role;
import org.example.taller2springboot.entity.RolePrivilege;
import org.example.taller2springboot.entity.RolePrivilegeId;
import org.example.taller2springboot.repository.PrivilegeRepository;
import org.example.taller2springboot.repository.RolePrivilegeRepository;
import org.example.taller2springboot.repository.RoleRepository;
import org.example.taller2springboot.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock private RoleRepository roleRepository;
    @Mock private PrivilegeRepository privilegeRepository;
    @Mock private RolePrivilegeRepository rolePrivilegeRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void getCount_shouldDelegate() {
        when(roleRepository.count()).thenReturn(3L);
        assertEquals(3L, roleService.getCount());
    }

    @Test
    void getRoles_shouldReturnList() {
        when(roleRepository.findAll()).thenReturn(List.of(new Role(), new Role()));
        assertEquals(2, roleService.getRoles().size());
    }

    @Test
    void findByName_shouldReturnOptional() {
        Role r = new Role(); r.setRoleId(1); r.setName("ADMIN");
        when(roleRepository.findByNameIgnoreCase("ADMIN")).thenReturn(Optional.of(r));
        assertTrue(roleService.findByName("ADMIN").isPresent());
    }

    @Test
    void createRole_whenNoPrivileges_shouldThrow() {
        Role r = new Role(); r.setName("EMPTY");
        assertThrows(RuntimeException.class, () -> roleService.createRole(r, Collections.emptyList()));
        assertThrows(RuntimeException.class, () -> roleService.createRole(r, null));
        verifyNoInteractions(privilegeRepository, rolePrivilegeRepository);
    }

    @Test
    void createRole_whenValid_shouldSaveAndAttachPrivileges() {
        Role toSave = new Role(); toSave.setName("MANAGER");
        Role saved = new Role(); saved.setRoleId(10); saved.setName("MANAGER");
        Privilege p1 = new Privilege(); p1.setPrivilegeId(100);
        Privilege p2 = new Privilege(); p2.setPrivilegeId(200);

        when(roleRepository.save(any(Role.class))).thenReturn(saved);
        when(privilegeRepository.findById(100)).thenReturn(Optional.of(p1));
        when(privilegeRepository.findById(200)).thenReturn(Optional.of(p2));
        when(roleRepository.getReferenceById(10)).thenReturn(saved);

        Role out = roleService.createRole(toSave, List.of(100, 200));

        assertNotNull(out);
        assertEquals(10, out.getRoleId());
        verify(rolePrivilegeRepository, times(2)).save(any(RolePrivilege.class));
    }

    @Test
    void updateRolePrivileges_whenRoleNotFound_shouldThrow() {
        when(roleRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> roleService.updateRolePrivileges(999, List.of(1)));
    }

    @Test
    void updateRolePrivileges_whenEmptyPrivileges_shouldThrow() {
        Role existing = new Role(); existing.setRoleId(5);
        when(roleRepository.findById(5)).thenReturn(Optional.of(existing));
        assertThrows(RuntimeException.class, () -> roleService.updateRolePrivileges(5, Collections.emptyList()));
    }

    @Test
    void updateRolePrivileges_whenValid_shouldDeleteOldAndInsertNew() {
        Role existing = new Role(); existing.setRoleId(5);
        Privilege p = new Privilege(); p.setPrivilegeId(77);

        // Role encontrado
        when(roleRepository.findById(5)).thenReturn(Optional.of(existing));
        when(roleRepository.getReferenceById(5)).thenReturn(existing);

        // Old links (dos del role=5, uno de otro role)
        RolePrivilege old1 = new RolePrivilege(); old1.setRolePrivilegeId(new RolePrivilegeId()); old1.getRolePrivilegeId().setRoleId(5); old1.getRolePrivilegeId().setPrivilegeId(1);
        RolePrivilege old2 = new RolePrivilege(); old2.setRolePrivilegeId(new RolePrivilegeId()); old2.getRolePrivilegeId().setRoleId(5); old2.getRolePrivilegeId().setPrivilegeId(2);
        RolePrivilege other = new RolePrivilege(); other.setRolePrivilegeId(new RolePrivilegeId()); other.getRolePrivilegeId().setRoleId(8); other.getRolePrivilegeId().setPrivilegeId(3);
        when(rolePrivilegeRepository.findAll()).thenReturn(List.of(old1, old2, other));

        when(privilegeRepository.findById(77)).thenReturn(Optional.of(p));

        // Capturamos el deleteAll para verificar que sólo borra los del role=5
        ArgumentCaptor<List<RolePrivilege>> deleteCaptor = ArgumentCaptor.forClass(List.class);

        Role out = roleService.updateRolePrivileges(5, List.of(77));

        assertNotNull(out);
        verify(rolePrivilegeRepository).deleteAll(deleteCaptor.capture());
        List<RolePrivilege> deleted = deleteCaptor.getValue();
        assertEquals(2, deleted.size());
        assertTrue(deleted.stream().allMatch(rp -> rp.getRolePrivilegeId().getRoleId().equals(5)));

        verify(rolePrivilegeRepository, times(1)).save(any(RolePrivilege.class));
    }

    @Test
    void deleteRole_shouldDelegate() {
        roleService.deleteRole(11);
        verify(roleRepository).deleteById(11);
    }
}
