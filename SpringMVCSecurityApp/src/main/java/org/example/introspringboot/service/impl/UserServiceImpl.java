package org.example.introspringboot.service.impl;

import org.example.introspringboot.entity.User;
import org.example.introspringboot.repository.UserRepository;
import org.example.introspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public void createUser(User user) {
        //Save the user
        userRepository.save(user);
    }
}
