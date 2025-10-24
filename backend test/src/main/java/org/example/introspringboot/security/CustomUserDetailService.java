package org.example.introspringboot.security;

import org.example.introspringboot.entity.User;
import org.example.introspringboot.repository.UserRepository;
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
        User user = userService.findByUsername(username);
        CustomUserDetails appUser = new CustomUserDetails(user);
        return appUser;
    }
}
