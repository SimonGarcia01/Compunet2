// RoleController.java
package org.example.taller3mvc.controller;

import org.example.taller3mvc.entity.Role;
import org.example.taller3mvc.service.PrivilegeService;
import org.example.taller3mvc.service.RoleService;
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
        model.addAttribute("roles", roleService.getRoles());
        return "roles/list";
    }

    // CREATE (form)
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        model.addAttribute("privileges", privilegeService.getPrivileges());
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
        Role role = roleService.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        model.addAttribute("role", role);
        model.addAttribute("privileges", privilegeService.getPrivileges());
        return "roles/edit-privileges";
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