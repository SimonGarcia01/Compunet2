// MessageRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {}
