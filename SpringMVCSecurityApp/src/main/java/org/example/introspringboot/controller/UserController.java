package org.example.introspringboot.controller;

import org.example.introspringboot.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public String profile(Model model, Authentication auth) {
        //getPrincipal returns object, and then it is down-casted to the specific implementation
        CustomUserDetails user= (CustomUserDetails) auth.getPrincipal();
        //Returns the username of the user
        model.addAttribute("user", user.getUsername());
        //returns the list of authorities of the user
        model.addAttribute("authorities", user.getAuthorities());

        return "user/profile";
    }
}
