// MessageRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {}
