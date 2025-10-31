package org.example.taller3mvc.controller;

import org.example.taller3mvc.security.CustomUserDetails;
import org.example.taller3mvc.service.RoleService;
import org.example.taller3mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users/list";
    }

    @GetMapping("/{id}/roles")
    public String editUserRoles(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findById(id).orElseThrow());
        model.addAttribute("roles", roleService.getRoles());
        return "users/edit-roles";
    }

    @PostMapping("/{id}/roles")
    public String updateUserRoles(@PathVariable Integer id,
                                  @RequestParam List<Integer> roleIds,
                                  RedirectAttributes ra) {
        userService.updateUserRoles(id, roleIds);
        ra.addFlashAttribute("success", "Roles actualizados correctamente");
        return "redirect:/users";
    }
}
