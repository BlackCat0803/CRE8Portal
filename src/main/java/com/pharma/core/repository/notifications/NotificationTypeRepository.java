package com.pharma.core.repository.notifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.notifications.NotificationType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("notificationTypeRepository")
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer>{

	
}
