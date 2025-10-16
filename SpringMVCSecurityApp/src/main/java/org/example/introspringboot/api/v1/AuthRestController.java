package org.example.introspringboot.api.v1;

import org.example.introspringboot.api.v1.dto.AuthRequest;
import org.example.introspringboot.api.v1.dto.AuthResponse;
import org.example.introspringboot.api.v1.dto.MessageResponse;
import org.example.introspringboot.security.CustomUserDetailService;
import org.example.introspringboot.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private CustomUserDetailService customUserDetailsService;

    //This is to handle the tokens
    @Autowired
    private JWTService jwtService;

    //This is to handle authentication
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){

        //First you must authenticate to know if that user and password are correct
        try{
            //This is calling user detail service which calls the user service
            //Goes through the user repository and getting to the DB
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
        //This method will throw an exception if the user and password don't match or are not found
        } catch(Exception e){
            return ResponseEntity.status(401).body(
                    //To make a more clear message we will use a standard Response Message
                    new MessageResponse(e.getMessage())
            );
        }

        //Then get the userDetails to make the token
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        //Now make the token using the userDetails
        String accessToken = jwtService.generateToken(userDetails);

        return ResponseEntity.status(200).body(new AuthResponse(accessToken));
    }
}
