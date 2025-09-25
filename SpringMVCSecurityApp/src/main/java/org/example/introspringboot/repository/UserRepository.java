package org.example.introspringboot.repository;

import org.example.introspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Find a user by its username
    Optional<User> findByUsername(String username);
}
