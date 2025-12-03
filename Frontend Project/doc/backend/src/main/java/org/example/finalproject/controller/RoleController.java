// RoleController.java
package org.example.finalproject.controller;

import org.example.finalproject.api.v1.dtos.RoleResponse;
import org.example.finalproject.entity.Role;
import org.example.finalproject.service.PrivilegeService;
import org.example.finalproject.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/roles")
@PreAuthorize("hasAuthority('Administrador')")
public class RoleController {

    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    public RoleController(RoleService roleService, PrivilegeService privilegeService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "roles/list";
    }

    // CREATE (form)
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        model.addAttribute("privileges", privilegeService.getAllPrivileges());
        return "roles/create";
    }

    // CREATE (submit)
    @PostMapping("/create")
    public String create(@ModelAttribute Role role,
                         @RequestParam(value = "privilegeIds", required = false) List<Integer> privilegeIds,
                         RedirectAttributes ra) {

        if (privilegeIds == null || privilegeIds.isEmpty()) {
            ra.addFlashAttribute("error", "Seleccione al menos un privilegio.");
            return "redirect:/roles/create";
        }
        roleService.createRole(role, privilegeIds);
        ra.addFlashAttribute("success", "Rol creado.");
        return "redirect:/roles";
    }

    // EDIT privilegios del rol
    @GetMapping("/{id}/edit-privileges")
    public String editPrivileges(@PathVariable Integer id, Model model) {
        try {
            RoleResponse role = roleService.findById(id);
            model.addAttribute("role", role);
            model.addAttribute("privileges", privilegeService.getAllPrivileges());
            return "roles/edit-privileges";
        }catch (RuntimeException e) {
            return "Rol no encontrado";
        }
    }

    // UPDATE privilegios
    @PostMapping("/{id}/edit-privileges")
    public String updatePrivileges(@PathVariable Integer id,
                                   @RequestParam(value = "privilegeIds", required = false) List<Integer> privilegeIds,
                                   RedirectAttributes ra) {
        if (privilegeIds == null || privilegeIds.isEmpty()) {
            ra.addFlashAttribute("error", "Seleccione al menos un privilegio.");
            return "redirect:/roles/" + id + "/edit-privileges";
        }
        roleService.updateRolePrivileges(id, privilegeIds);
        ra.addFlashAttribute("success", "Privilegios actualizados.");
        return "redirect:/roles";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        roleService.deleteRole(id);
        ra.addFlashAttribute("success", "Rol eliminado.");
        return "redirect:/roles";
    }
}