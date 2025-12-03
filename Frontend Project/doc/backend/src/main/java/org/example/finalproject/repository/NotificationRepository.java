// NotificationRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    boolean existsByTitle(String title);
    Optional<Notification> findByTitle(String title);
}
