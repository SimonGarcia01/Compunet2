package org.example.introspringboot.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    @Value("${app.apikey}")
    private String apiKeyValue;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("Key");
        if(apiKey != null){
            if(apiKey.equals(apiKeyValue)){
                filterChain.doFilter(request, response);
            }
        } else {
            response.setStatus(400);
            response.getWriter().write("Missing API Key");
            response.getWriter().flush();
        }
    }
}
