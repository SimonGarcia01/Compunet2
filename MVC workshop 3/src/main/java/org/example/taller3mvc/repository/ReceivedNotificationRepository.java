// ReceivedNotificationRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.ReceivedNotification;
import org.example.taller3mvc.entity.ReceivedNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivedNotificationRepository extends JpaRepository<ReceivedNotification, ReceivedNotificationId> {}
