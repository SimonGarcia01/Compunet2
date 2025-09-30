package org.example.introspringboot.service;

import org.example.introspringboot.entity.User;

public interface UserService {
    //Return a user found by the username
    //The Optional<> will be managed within the implementation
    User findByUsername(String username);

    public void createUser(User user);
}
