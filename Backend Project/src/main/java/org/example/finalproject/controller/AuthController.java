package org.example.finalproject.controller;

import org.example.finalproject.entity.User;
import org.example.finalproject.service.RoleService;
import org.example.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/create-user")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "auth/create-user";
    }
    private static final Pattern ICE_DOMAIN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@u\\.icesi\\.edu\\.co$");
    @PostMapping("/create-user")
    public String createUser(@ModelAttribute User user,
                             @RequestParam(value = "selectedRoles", required = false) List<Integer> roleIds,
                             RedirectAttributes ra) {

        System.out.println("=== [DEBUG] Iniciando creación de usuario ===");
        System.out.println("Datos recibidos -> email: " + user.getEmail() + ", roles: " + roleIds);

        // 1) Normalizar y validar el email institucional
        String email = (user.getEmail() == null) ? null : user.getEmail().trim().toLowerCase();
        if (email == null || !ICE_DOMAIN.matcher(email).matches()) {
            System.out.println("❌ Email no válido: " + email);
            ra.addFlashAttribute("error", "Debe usar correo institucional @u.icesi.edu.co");
            return "redirect:/auth/create-user";
        }
        user.setEmail(email);

        // 2) Validar roles
        if (roleIds == null || roleIds.isEmpty()) {
            System.out.println("❌ No se seleccionaron roles.");
            ra.addFlashAttribute("error", "Debe seleccionar al menos un rol.");
            return "redirect:/auth/create-user";
        }

        // 3) Validar duplicado
        System.out.println("Comprobando si ya existe el email...");
        if (userService.existsByEmail(email)) {
            System.out.println("❌ Email duplicado: " + email);
            ra.addFlashAttribute("error", "El correo ya está registrado.");
            return "redirect:/auth/create-user";
        }

        // 4) Validar contraseña
        String rawPassword = user.getEncryptedPassword();
        System.out.println("Password recibido (raw): " + rawPassword);
        if (rawPassword == null || rawPassword.isBlank()) {
            ra.addFlashAttribute("error", "La contraseña es obligatoria.");
            return "redirect:/auth/create-user";
        }

        if (rawPassword.length() < 8) {
            System.out.println("❌ Contraseña demasiado corta.");
            ra.addFlashAttribute("error", "La contraseña debe tener al menos 8 caracteres.");
            return "redirect:/auth/create-user";
        }

        // 5) Aplicar BCrypt
        user.setEncryptedPassword(passwordEncoder.encode(rawPassword));
        System.out.println("Contraseña encriptada correctamente.");

        // 6) Defaults
        user.setActive(true);
        if (user.getCreationDate() == null) {
            user.setCreationDate(LocalDate.now());
        }

        // 7) Guardar
        try {
            System.out.println("Llamando a userService.createUser()...");
            userService.createUser(user, roleIds);
            System.out.println("✅ Usuario creado correctamente.");
        } catch (Exception e) {
            System.out.println("💥 Error al crear usuario: " + e.getMessage());
            e.printStackTrace();
            ra.addFlashAttribute("error", "No se pudo crear el usuario: " + e.getMessage());
            return "redirect:/auth/create-user";
        }

        // 8) Éxito
        ra.addFlashAttribute("success", "Usuario creado correctamente. Inicie sesión.");
        System.out.println("=== [DEBUG] Creación completada ===");
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        System.out.println("=== [LOGIN GET] /auth/login ===");
        if (error != null) {
            System.out.println("Param error=true -> Hubo error previo de autenticación");
        }
        if (logout != null) {
            System.out.println("Param logout=true -> Cierre de sesión exitoso");
        }
        model.addAttribute("user", new User());
        return "auth/login";
    }
    //Springboot makes the automatic @Post for the login

}
