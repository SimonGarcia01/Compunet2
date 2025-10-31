// NotificationRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {}
