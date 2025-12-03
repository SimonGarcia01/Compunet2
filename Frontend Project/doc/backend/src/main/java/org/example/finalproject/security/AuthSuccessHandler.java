// AuthSuccessHandler.java
package org.example.finalproject.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws java.io.IOException {
        System.out.println("[AUTH] ✅ Login OK de: " + authentication.getName());
        response.sendRedirect("/home"); // o la URL que uses
    }
}