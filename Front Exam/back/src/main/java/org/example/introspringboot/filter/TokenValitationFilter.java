package org.example.introspringboot.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.introspringboot.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class TokenValitationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            authorization = authorization.replace("Bearer ", "");
            System.out.println("*****");
            System.out.println(authorization);
            try {
                Claims claims = jwtService.parseToken(authorization);
                String subject = claims.getSubject();
                String email = claims.get("email", String.class);
                List<String> authorities = claims.get("authorities", List.class);
                List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(
                        name -> new SimpleGrantedAuthority(name)
                ).toList();

                System.out.println("subject = " + subject);
                System.out.println("email = " + email);
                System.out.println("authorities = " + authorities);

                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(
                                email, null, grantedAuthorities
                        );
                    SecurityContextHolder.getContext().setAuthentication(token);

                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw new RuntimeException("Invalid or expired token: " + e.getMessage(), e);
            }
        }else{
            filterChain.doFilter(request, response);
        }

    }
}
