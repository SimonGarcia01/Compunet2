package org.example.finalproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    @Value("${app.security.secretKey}")
    private String secretKey;

    @Value("${app.security.expirationMinutes}")
    private int expirationMinutes;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000L * 60L * expirationMinutes);

        return Jwts.builder()
                //add to the token the user
                .setSubject(userDetails.getUsername())
                //set the time it was generated
                .setIssuedAt(now)
                //Set the expiration date for that token
                .setExpiration(expiry)
                //Now use the method to add the payload
                //.setClaims resets everything before so it's changed into add
                .addClaims(
                        createClaims(
                                userDetails
                        )
                )
                //to make the key
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    //Make a map that holds the username and the privileges into strings
    public Map<String, Object> createClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        claims.put("authorities",
                userDetails.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .toList());
        return claims;
    }

    //Now we make a method to get the claims we want from the token
    public Claims parseToken(String token) {
        try {
            //To get the info you need the secretkey of the server to actually translate the info
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    //Go through the claims of the token
                    .parseClaimsJws(token)
                    //
                    .getBody();
            return claims;

            // If the token expires
        } catch (ExpiredJwtException e) {
            System.out.println("token expired");
            throw e;
            //If the content of the payload is edited
        } catch (SignatureException e) {
            System.out.println("Token signature exception");
            throw e;
            //If the token doesn't have those three parts specifically
        } catch (MalformedJwtException e) {
            System.out.println("Token malformed exception");
            throw e;
        }
    }
}