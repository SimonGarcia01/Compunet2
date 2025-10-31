package org.example.taller2springboot.integration.service;

import org.example.taller2springboot.entity.Privilege;
import org.example.taller2springboot.repository.PrivilegeRepository;
import org.example.taller2springboot.service.impl.PrivilegeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivilegeServiceImplTest {

    @Mock private PrivilegeRepository privilegeRepository;

    @InjectMocks
    private PrivilegeServiceImpl privilegeService;

    @Test
    void getCount_shouldDelegate() {
        when(privilegeRepository.count()).thenReturn(9L);
        assertEquals(9L, privilegeService.getCount());
    }

    @Test
    void findById_shouldReturnOptional() {
        Privilege p = new Privilege(); p.setPrivilegeId(1); p.setName("READ");
        when(privilegeRepository.findById(1)).thenReturn(Optional.of(p));

        Optional<Privilege> out = privilegeService.findById(1);
        assertTrue(out.isPresent());
        assertEquals("READ", out.get().getName());
    }

    @Test
    void findByName_shouldReturnOptional() {
        Privilege p = new Privilege(); p.setPrivilegeId(2); p.setName("WRITE");
        when(privilegeRepository.findByNameIgnoreCase("write")).thenReturn(Optional.of(p));

        assertTrue(privilegeService.findByName("write").isPresent());
    }

    @Test
    void createPrivilege_shouldSave() {
        Privilege toSave = new Privilege(); toSave.setName("EXECUTE");
        Privilege saved = new Privilege(); saved.setPrivilegeId(5); saved.setName("EXECUTE");

        when(privilegeRepository.save(any(Privilege.class))).thenReturn(saved);

        Privilege out = privilegeService.createPrivilege(toSave);
        assertEquals(5, out.getPrivilegeId());
        assertEquals("EXECUTE", out.getName());
    }

    @Test
    void deletePrivilege_shouldDelegate() {
        privilegeService.deletePrivilege(7);
        verify(privilegeRepository).deleteById(7);
    }
}
