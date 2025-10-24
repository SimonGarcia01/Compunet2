package org.example.introspringboot.service.impl;

import org.example.introspringboot.entity.User;
import org.example.introspringboot.repository.UserRepository;
import org.example.introspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public void createUser(User user) {
        String bcryptPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPass);
        userRepository.save(user);
    }

}
