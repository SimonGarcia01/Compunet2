package org.example.introspringboot.security;

import org.example.introspringboot.entity.User;
import org.example.introspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Get the user from the DB
        User user = userService.findByUsername(username);
        if(user!=null){
            //Map the user from the DB into the one in the Spring version
            CustomUserDetails appUser = new CustomUserDetails(user);
            return appUser;
        }else{
            throw new RuntimeException("Username not found");
        }
    }
}
