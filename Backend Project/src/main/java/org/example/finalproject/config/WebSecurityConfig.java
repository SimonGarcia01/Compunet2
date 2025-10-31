package org.example.finalproject.config;

import jakarta.servlet.http.HttpServletResponse;
import org.example.finalproject.filter.TokenValidationFilter;
import org.example.finalproject.security.AuthFailureHandler;
import org.example.finalproject.security.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.securityMatcher("/api/v1/**").
                authorizeHttpRequests(auth -> auth.
                        //To give access to everything in the REST api part
                        //Now we are going to add security
                        //Only permit all on login
                                requestMatchers("/api/v1/auth/login").permitAll().
                        //Any other request will need to be authenticated
                                anyRequest().authenticated()
                )
                //Added the token validation filter before so it's used by the UsernamePasswordAuthenticationFilter
                .addFilterBefore(tokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
                //Make sure the session is set to stateless for rest
                .sessionManagement(
                        sessionManagement -> sessionManagement.
                                sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        //The csrf token is disabled
                ).csrf(csrf -> csrf.disable())

                //Now we are blocking the app using exceptions
                .exceptionHandling(ex -> ex
                        //If the user doesn't have the right credentials it will be blocked = 401
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Unauthorized\"}");
                        })
                        //If the user doesn't have the right level of authorities he is also blocked = 403
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Forbidden\"}");
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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/create-user", "/auth/login").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**", "/resources/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(authSuccessHandler)   // <—
                        .failureHandler(authFailureHandler)   // <—
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }
}
