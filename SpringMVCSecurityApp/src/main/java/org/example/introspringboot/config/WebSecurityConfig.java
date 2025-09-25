package org.example.introspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig {
    //This bean is so that the entered password is not tried to be encrypted
    //This is not safe! Later this will be commented.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
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

