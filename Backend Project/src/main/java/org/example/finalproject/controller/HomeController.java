package org.example.finalproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication auth, Model model) {
        model.addAttribute("auth", auth);
        return "auth/home"; // porque el HTML está en templates/auth/home.html
    }

    // opcional: redirigir raíz → /home
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/home";
    }
}