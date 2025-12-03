package org.example.finalproject.controller;

import org.example.finalproject.service.RoleService;
import org.example.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/{id}/roles")
    public String editUserRoles(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.getAllRoles());
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
