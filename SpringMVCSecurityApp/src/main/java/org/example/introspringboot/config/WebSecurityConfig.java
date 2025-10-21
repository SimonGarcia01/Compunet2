package org.example.introspringboot.config;

import jakarta.servlet.http.HttpServletResponse;
import org.example.introspringboot.filter.TokenValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    //This is added to then use the token validator filter
    @Autowired
    private TokenValidationFilter tokenValidationFilter;

    //This is to make our own implementation of the authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //This bean is so that the entered password is not tried to be encrypted
    //This is not safe! Later this will be commented.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //with no hashing the password
//        return NoOpPasswordEncoder.getInstance();
    }

    //First filter for the DB
    @Bean
    @Order(1)
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                //Added security matcher so it only works for that specific URL
                .securityMatcher(toH2Console())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(toH2Console()).permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(toH2Console())
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                );
        return http.build();
    }

    //Bean to give access to the REST api part
    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.
                securityMatcher("/api/v1/**").
                authorizeHttpRequests(auth -> auth.
                        //To give access to everything in the REST api part
                        //Now we are going to add security
                        //Only permit all on login
                        requestMatchers("/api/v1/auth/login/").permitAll().
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

    //Third filter for the rest of the app now
    @Bean
    @Order(3)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.
                        //For the first try for REST class
                        requestMatchers("/api/v1/**").permitAll().

                        //Let anyone connect yo the /auth/signup
                        requestMatchers("/auth/signup").permitAll().
                        //Give access to the static css files
                        requestMatchers("/css/**").permitAll().

                        //Give access to the student list only to a professor
                        //requestMatchers("/students/").hasAnyRole("PROFESSOR").

                        //Any other request will need to be authenticated
                        anyRequest().authenticated()
        )
                //Adding the form login makes it so any route other than public ones
                //will be sent to the login page
                .formLogin(login -> login
                .loginPage("/auth/login")
                .defaultSuccessUrl("/user/me", true)
                .permitAll()
                //Now add the logout condition
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsMngr = new InMemoryUserDetailsManager();
//
//        UserDetails user = User.withUsername("user1") //Change the user
//                .password("123456") //Specify the password
//                .authorities("read") // Represent the authorities the user has
//                .build();
//
//        userDetailsMngr.createUser(user); //Add the user to the list of users
//
//        return userDetailsMngr; // Return the users
//    }
}

