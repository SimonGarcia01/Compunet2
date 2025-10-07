// NotificationRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {}
