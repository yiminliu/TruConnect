package com.trc.dao;

import java.util.Collection;

import com.trc.domain.support.notification.NotificationMessage;
import com.trc.domain.support.notification.NotificationPage;
import com.trc.domain.support.notification.NotificationSchedule;

public interface NotificationDao {

	public Integer saveNotificationSchedule(NotificationSchedule notificationSchedule);
	
	public Integer saveNotificationMessage(NotificationMessage notificationMessage);
	
	public Integer saveNotificationPage(NotificationPage notificationPage);
	
	public Collection<NotificationSchedule> getAllScheduledNotifications();
	
	public NotificationSchedule getNotificationById(int id);
	
	public Collection<NotificationMessage> getAllNotificationMessages();
	
	public Collection<NotificationPage> getAllNotificationPages();	
	
	public void modify(NotificationSchedule notificationSchedule);	
	
	public void deleteNotification(int id);	
	
	public NotificationMessage findNotificationMessageById(Integer id);
}
