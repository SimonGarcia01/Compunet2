package org.example.introspringboot.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.introspringboot.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    //This filter will get the request and the response
    //Has the servlet objects to get the request and return (but it's not a servlet)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Authorization: Bearer <token>
        // Key = Authorization
        // Value = Bearer <token>
        String authorization = request.getHeader("Authorization");
        //Check the request comes with the authorization info
        if(authorization != null && authorization.startsWith("Bearer ")) {
            //Remove the Bearer word
            authorization = authorization.replace("Bearer ", "");
            //To verify the information
            System.out.printf("TokenValidationFilter authorization: %s\n", authorization);

            try{
                //Use the JwtService to ge the claims of the token
                Claims claims = jwtService.parseToken(authorization);
                //This is the owner of the token
                String subject = claims.getSubject();

                //This gets the email from the claims
                String email = claims.get("email", String.class);

                System.out.println("subject = " + subject);
                System.out.println("email = " + email);

                //Get the list of authorities
                List<String> authorities = claims.get("authorities", List.class);
                System.out.println("authorities = " + authorities);

                //The authorities need to be changed into GrantedAuthorities from the strings
                List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(
                        name -> new SimpleGrantedAuthority(name)
                ).toList();

                //Now lets do actual validation
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(
                                //The email is the subject
                                email,
                                //The credentials are null since it's the password and is dangerous to carry it in a token
                                null,
                                //Use the list of granted authorities
                                grantedAuthorities
                        );

                //Here you actually authorize the request
                SecurityContextHolder.getContext().setAuthentication(token);

                //This allows to get the request and then pass it to the next part
                //If it's not here, this will resend a 200 status with nothing
                filterChain.doFilter(request, response);
            } catch(Exception e){
                //This is to catch the exceptions made from the jwtService.parseToken()
                response.setStatus(400);
                response.getWriter().write(e.getMessage());
                response.getWriter().flush();
            }

        } else {
            //If it doesn't have the authorization, maybe is trying to access a public acces part
            filterChain.doFilter(request, response);
        }

    }

}
