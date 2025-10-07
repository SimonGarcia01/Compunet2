// UserRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {}
