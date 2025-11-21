package org.example.introspringboot.security;

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
public class JwtService {

    @Value("${app.security.secretkey}")
    private String secret;
    @Value("${app.security.expirationMinutes}")
    private int expirationMinutes;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMinutes * 60 * 1000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .addClaims(
                        createClaims(
                                userDetails
                        )
                )
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Map<String, Object> createClaims(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        claims.put("authorities",
                userDetails.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .toList());
        return claims;
    }

    public Claims parseToken(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        }catch (ExpiredJwtException e){
            System.out.println("token expired");
            throw e;
        }catch (SignatureException e){
            System.out.println("token signature error");
            throw e;
        }catch (MalformedJwtException e){
            System.out.println("token malformed error");
            throw e;
        }
    }



}
