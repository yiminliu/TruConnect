package com.trc.manager;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.trc.domain.support.notification.NotificationMessage;
import com.trc.domain.support.notification.NotificationPage;
import com.trc.domain.support.notification.NotificationSchedule;

@Service
public interface NotificationManager {

    public Integer saveNotificationSchedule(NotificationSchedule notificationSchedule);
    
    public Integer saveNotificationMessage(NotificationMessage notificationMessage);
    
    public Integer saveNotificationPage(NotificationPage notificationPage);
	
	public void modify(NotificationSchedule notificationSchedule);	
	
	public void deleteNotification(int id);	
	
	public Collection<NotificationSchedule> getAllScheduledNotifications();
	
	public NotificationSchedule getNotificationById(int id);
	
	public Collection<NotificationMessage> getSelectededNotificationMessages(String pageName);
  
	public Collection<NotificationMessage> getActiveNotificationMessages();
	
	public Collection<NotificationMessage> getAllNotificationMessages();
	
	public Collection<NotificationPage> getAllNotificationPages();
	
	public NotificationMessage getNotificationMessageById(int id);

}
