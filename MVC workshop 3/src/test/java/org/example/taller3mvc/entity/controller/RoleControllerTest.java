package org.example.taller3mvc.entity.controller;

import org.example.taller3mvc.controller.RoleController;
import org.example.taller3mvc.entity.Privilege;
import org.example.taller3mvc.entity.Role;
import org.example.taller3mvc.service.PrivilegeService;
import org.example.taller3mvc.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@ExtendWith(MockitoExtension.class)
@Import({RoleService.class, PrivilegeService.class}) // se “inyectan” manualmente
class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private RoleService roleService;

    @Mock
    private PrivilegeService privilegeService;

    @Test
    @WithMockUser(authorities = "Administrador")
    void listRoles_ok() throws Exception {
        given(roleService.getRoles()).willReturn(List.of(new Role()));

        mvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/list"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    @WithMockUser(authorities = "Administrador")
    void createRole_post_ok() throws Exception {
        given(roleService.createRole(Mockito.any(), Mockito.anyList())).willReturn(new Role());
        given(privilegeService.getPrivileges()).willReturn(List.of(new Privilege(1, "PV", "")));

        mvc.perform(post("/roles/create").with(csrf())
                        .param("name", "Admin")
                        .param("privilegeIds", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/roles"));
    }

    @Test
    @WithMockUser(authorities = "Administrador")
    void editPrivilegesForm_ok() throws Exception {
        Role role = new Role();
        role.setRoleId(5);
        role.setName("R");

        given(roleService.findById(5)).willReturn(Optional.of(role));
        given(privilegeService.getPrivileges()).willReturn(List.of());

        mvc.perform(get("/roles/5/edit-privileges"))
                .andExpect(status().isOk())
                .andExpect(view().name("roles/edit-privileges"))
                .andExpect(model().attributeExists("role", "privileges"));
    }
}