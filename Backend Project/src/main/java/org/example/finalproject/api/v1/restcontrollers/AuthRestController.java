package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.AuthRequest;
import org.example.finalproject.api.v1.dtos.AuthResponse;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.security.CustomUserDetailService;
import org.example.finalproject.security.JWTService;
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
    private CustomUserDetailService customUserDetailsService;

    //This is to handle the tokens
    @Autowired
    private JWTService jwtService;

    //This is to handle authentication
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        //First you must authenticate to know if that user and password are correct
        try{
            //This is calling user detail service which calls the user service
            //Goes through the user repository and getting to the DB
            //This is done so you don't have to do the authentication yourself
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
        //This method will throw an exception if the user and password don't match or are not found
        } catch(Exception e){
            return ResponseEntity.status(401).body(
                    //To make a more clear message we will use a standard Response Message
                    new MsgResp(e.getMessage())
            );
        }

        //Then get the userDetails to make the token
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        //Now make the token using the userDetails
        String accessToken = jwtService.generateToken(userDetails);

        return ResponseEntity.status(200).body(new AuthResponse(accessToken));
    }
}
