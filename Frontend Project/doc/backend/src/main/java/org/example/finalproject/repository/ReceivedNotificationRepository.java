// ReceivedNotificationRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.ReceivedNotification;
import org.example.finalproject.entity.ReceivedNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedNotificationRepository extends JpaRepository<ReceivedNotification, ReceivedNotificationId> {}
