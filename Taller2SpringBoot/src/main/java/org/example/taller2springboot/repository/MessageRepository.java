// MessageRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {}
