// src/main/java/org/example/finalproject/api/v1/restcontrollers/AuthRestController.java
package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.AuthRequest;
import org.example.finalproject.api.v1.dtos.AuthResponse;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.api.v1.dtos.RegisterRequest;
import org.example.finalproject.entity.User;
import org.example.finalproject.security.CustomUserDetailService;
import org.example.finalproject.security.JWTService;
import org.example.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired private CustomUserDetailService customUserDetailsService;
    @Autowired private JWTService jwtService;
    @Autowired private AuthenticationManager authenticationManager;

    // 👇 añadidos para register
    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;

    private static final Pattern ICE_DOMAIN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@u\\.icesi\\.edu\\.co$");

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MsgResp(e.getMessage()));
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String accessToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(accessToken));
    }

    // 🆕 Registro rest
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        // 1) Validaciones básicas
        if (req.getEmail() == null || !ICE_DOMAIN.matcher(req.getEmail().trim().toLowerCase()).matches()) {
            return ResponseEntity.badRequest().body(new MsgResp("Debe usar correo institucional @u.icesi.edu.co"));
        }
        if (req.getPassword() == null || req.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new MsgResp("La contraseña debe tener al menos 8 caracteres."));
        }
        if (req.getRoleIds() == null || req.getRoleIds().isEmpty()) {
            return ResponseEntity.badRequest().body(new MsgResp("Debe seleccionar al menos un rol."));
        }
        String email = req.getEmail().trim().toLowerCase();
        if (userService.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MsgResp("El correo ya está registrado."));
        }

        // 2) Construir entidad y persistir
        User user = new User();
        user.setEmail(email);
        user.setEncryptedPassword(passwordEncoder.encode(req.getPassword())); // BCrypt
        user.setActive(true);
        user.setCreationDate(LocalDate.now());

        // Si tu entidad tiene estos campos, setéalos (si no, elimina estas líneas)
        if (req.getFullName() != null) user.setName(req.getFullName());
        if (req.getPersonalId() != null) user.setPersonalId(req.getPersonalId());

        try {
            userService.createUser(user, req.getRoleIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgResp("Usuario creado correctamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MsgResp("Error al crear usuario: " + e.getMessage()));
        }
    }
}