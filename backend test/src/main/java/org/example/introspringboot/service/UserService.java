package org.example.introspringboot.service;

import org.example.introspringboot.entity.User;

public interface UserService {
    User findByUsername(String username);
    void createUser(User user);
}
