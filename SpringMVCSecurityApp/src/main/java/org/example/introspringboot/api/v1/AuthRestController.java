package org.example.introspringboot.api.v1;

import org.example.introspringboot.api.v1.dto.AuthRequest;
import org.example.introspringboot.api.v1.dto.AuthResponse;
import org.example.introspringboot.security.CustomUserDetailService;
import org.example.introspringboot.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){

        //First you must authenticate to know if that user and password are correct

        //Then get the userDetails to make the token
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        //Now make the token using the userDetails
        String accessToken = jwtService.generateToken(userDetails);

        return ResponseEntity.status(200).body(new AuthResponse(accessToken));
    }
}
