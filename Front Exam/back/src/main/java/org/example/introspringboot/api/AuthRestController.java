package org.example.introspringboot.api;


import org.example.introspringboot.api.dto.AuthRequest;
import org.example.introspringboot.api.dto.AuthResponse;
import org.example.introspringboot.api.dto.MessageResponse;
import org.example.introspringboot.security.CustomUserDetailService;
import org.example.introspringboot.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
            UserDetails userDetails = customUserDetailService
                    .loadUserByUsername(authRequest.getUsername());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.status(200).body(new AuthResponse(token));
        }catch (Exception e) {
            return ResponseEntity.status(401).body(
                    new MessageResponse(e.getMessage())
            );
        }
    }

}
