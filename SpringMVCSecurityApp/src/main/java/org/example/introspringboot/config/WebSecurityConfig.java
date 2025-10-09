package org.example.introspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
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

    //Second filter for the rest of the app
    @Bean
    @Order(2)
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

                //To disable csrf token for the time being to use postman
        ).csrf( csrf -> csrf.disable())

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

