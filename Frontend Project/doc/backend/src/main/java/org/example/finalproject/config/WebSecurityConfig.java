package org.example.finalproject.config;

import jakarta.servlet.http.HttpServletResponse;
import org.example.finalproject.filter.TokenValidationFilter;
import org.example.finalproject.security.AuthFailureHandler;
import org.example.finalproject.security.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private TokenValidationFilter tokenValidationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // H2 console
    @Bean
    @Order(1)
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(toH2Console())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(toH2Console()).permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/v1/**")
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/register").permitAll() // 👈 nuevo
                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req,res,ex0) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })
                        .accessDeniedHandler((req,res,ex1) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Forbidden\"}");
                        })
                );
        return http.build();
    }

    // App
    @Autowired
    AuthSuccessHandler authSuccessHandler;
    @Autowired
    AuthFailureHandler authFailureHandler;

    @Bean
    @Order(3)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {}) // <- HABILITAR CORS también aquí si el front llama rutas /auth/*
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/create-user", "/auth/login").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**", "/resources/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(authSuccessHandler)
                        .failureHandler(authFailureHandler)
                        .permitAll()
                )
                .logout(lo -> lo
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        // En dev especifica el origen exacto de tu front:
        cfg.setAllowedOrigins(List.of("http://localhost:8081"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","X-Requested-With"));
        // cookies o withCredentials en Axios:
        cfg.setAllowCredentials(true);
        // exponer headers (p. ej. para descargas):
        cfg.setExposedHeaders(List.of("Content-Disposition","Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
