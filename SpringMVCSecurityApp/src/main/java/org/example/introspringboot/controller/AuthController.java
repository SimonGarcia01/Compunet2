package org.example.introspringboot.controller;

import org.example.introspringboot.entity.User;
import org.example.introspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(Model model){
        //Send the eggshell to sign in a user
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user){
        //Hashing the password before it is stored
        String bcryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        //Save the user
        userService.createUser(user);
        return "redirect:/auth/login";
    }

    //Get the url for the login
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "auth/login";
    }

    //Springboot makes the automatic @Post for the login

}
