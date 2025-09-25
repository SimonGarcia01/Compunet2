package org.example.introspringboot.security;

import org.example.introspringboot.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private User userDB;

    //The UserDetails is created from a User from the DB
    public CustomUserDetails(User user) {
        this.userDB = user;
    }

    @Override
    public String getPassword() {
        return userDB.getPassword();
    }

    @Override
    public String getUsername() {
        return userDB.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
