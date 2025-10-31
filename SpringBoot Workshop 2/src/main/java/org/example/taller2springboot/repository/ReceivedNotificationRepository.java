// ReceivedNotificationRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.ReceivedNotification;
import org.example.taller2springboot.entity.ReceivedNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivedNotificationRepository extends JpaRepository<ReceivedNotification, ReceivedNotificationId> {}
