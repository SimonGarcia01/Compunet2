// PrivilegeController.java
package org.example.taller3mvc.controller;

import org.example.taller3mvc.entity.Privilege;
import org.example.taller3mvc.service.PrivilegeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/privileges")
@PreAuthorize("hasAuthority('Administrador')")
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("privileges", privilegeService.getPrivileges());
        return "privilege/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("privilege", new Privilege());
        return "privilege/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Privilege privilege, RedirectAttributes ra) {
        try {
            privilegeService.createPrivilege(privilege);
            ra.addFlashAttribute("success", "Privilegio creado");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/privileges/create";
        }
        return "redirect:/privileges";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var p = privilegeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No encontrado"));
        model.addAttribute("privilege", p);
        return "privilege/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, @ModelAttribute Privilege privilege, RedirectAttributes ra) {
        try {
            privilegeService.updatePrivilege(id, privilege);
            ra.addFlashAttribute("success", "Actualizado");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/privileges/" + id + "/edit";
        }
        return "redirect:/privileges";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            privilegeService.deletePrivilege(id);
            ra.addFlashAttribute("success", "Eliminado");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/privileges";
    }
}