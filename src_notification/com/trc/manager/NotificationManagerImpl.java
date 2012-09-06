package com.trc.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trc.dao.NotificationDao;
import com.trc.dao.HibernateNotificationDao;
import com.trc.domain.support.notification.NotificationMessage;
import com.trc.domain.support.notification.NotificationPage;
import com.trc.domain.support.notification.NotificationSchedule;

@Service
public class NotificationManagerImpl implements NotificationManager {

	private Collection<NotificationSchedule> allSecheduledNotifications = new HashSet<NotificationSchedule>();
	
	@Autowired
	private NotificationDao notificationDao;
		
	@Override
	public Collection<NotificationSchedule> getAllScheduledNotifications(){
		allSecheduledNotifications = notificationDao.getAllScheduledNotifications();
		return allSecheduledNotifications;
	}
	
	@Override
	public NotificationSchedule getNotificationById(int id){
		return notificationDao.getNotificationById(id);
	}
	
	@Override
	public Collection<NotificationMessage> getSelectededNotificationMessages(String pageName){
		//List<NotificationMessage> messageList = new ArrayList<NotificationMessage>();
		Set<NotificationMessage> messages = new HashSet<NotificationMessage>();
		DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance(DateTimeFieldType.minuteOfHour()); 
		for(NotificationSchedule notificationSchedule : allSecheduledNotifications){
			notificationSchedule.getNotificationDateTime().initDateTime();
			if(dateTimeComparator.compare(DateTime.now(), notificationSchedule.getNotificationDateTime().getStartDateTime()) >= 0 &&
		       dateTimeComparator.compare(DateTime.now(), notificationSchedule.getNotificationDateTime().getEndDateTime()) <0){		
			   //diffInMinute < notificationSchedule.getNotificationDateTime().getDuration()) {		
			   for(NotificationPage page : notificationSchedule.getTargetPages()){
				   if(page.getName() != null && page.getName().endsWith(pageName)) {
					   messages.add(notificationSchedule.getMessage()); 
				 //     return page.getMessages();	
				   }
				
			   }
    		}
		}
		return messages;		
	}
	
	@Override
	public Set<NotificationMessage> getActiveNotificationMessages(){
		//List<NotificationMessage> messageList = new ArrayList<NotificationMessage>();
		Set <NotificationMessage> messages = new HashSet<NotificationMessage>();
		DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance(DateTimeFieldType.minuteOfHour()); 
		for(NotificationSchedule notificationSchedule : allSecheduledNotifications){
			if(notificationSchedule != null && notificationSchedule.getNotificationDateTime() != null) {
		       notificationSchedule.getNotificationDateTime().initDateTime();
			   //long diffInMinute = (DateTime.now().getMillis() - notificationSchedule.getNotificationDateTime().getScheduledDateTime().getMillis())/1000/60;
			   //System.out.println("diffInMinute = "+diffInMinute);
			   if(dateTimeComparator.compare(DateTime.now(), notificationSchedule.getNotificationDateTime().getStartDateTime()) >= 0 &&
		          dateTimeComparator.compare(DateTime.now(), notificationSchedule.getNotificationDateTime().getEndDateTime()) <0){		
			      //diffInMinute < notificationSchedule.getNotificationDateTime().getDuration()) {	
				  messages.add(notificationSchedule.getMessage()); 
			      //messageList.add(notificationSchedule.getMessage()); 
		       }
			}   
		}    	
		//return messageList;	
		return messages;
	}
	
	@Override
	public Set<NotificationMessage> getAllNotificationMessages(){		
	     Set<NotificationMessage> messageSet = new HashSet<NotificationMessage>(notificationDao.getAllNotificationMessages());
	     messageSet.remove(null);
	     return messageSet;
	}
	
	@Override
	public Collection<NotificationPage> getAllNotificationPages(){
		 Set<NotificationPage> pageSet = new HashSet<NotificationPage>(notificationDao.getAllNotificationPages());
	     //pageSet.remove(null);
	     return pageSet;
	}
	
	public NotificationMessage getNotificationMessageById(int id){
		return notificationDao.findNotificationMessageById(id);
	}	
		
	@Override
	public Integer saveNotificationSchedule(NotificationSchedule notificationSchedule) {
		List<NotificationPage> pages = notificationSchedule.getTargetPages();
        for(NotificationPage page : pages){
		//for(int i = 0; i<pages.size(); i++){
			//NotificationPage page = pages.get(i);
			page.setNotificationSchedule(notificationSchedule);
			//notificationSchedule.addTargetPage(page);
		}		
		return notificationDao.saveNotificationSchedule(notificationSchedule);
	}
	
	public Integer saveNotificationMessage(NotificationMessage notificationMessage){
		return notificationDao.saveNotificationMessage(notificationMessage);
	}
	
	@Override
	public Integer saveNotificationPage(NotificationPage notificationPage){
		return notificationDao.saveNotificationPage(notificationPage);
	}
	

	@Override
	public void modify(NotificationSchedule notificationSchedule) {
		notificationDao.modify(notificationSchedule);
	}

	@Override
	public void deleteNotification(int id) {
		notificationDao.deleteNotification(id);
	}
		
	
	@Scheduled(fixedRate=1000 * 60 * 2)
	public void checkSchedule(){
		System.out.println("Starting the chechSchedule task...");	
		allSecheduledNotifications = getAllScheduledNotifications();	
		System.out.println(" allSecheduledNotifications = "+allSecheduledNotifications.toString());
	}
	
	
    private void initForTest() {
		  	
	  	ApplicationContext appCtx = new ClassPathXmlApplicationContext("application_context.xml");
	  	try{
	  	   notificationDao = (NotificationDao)appCtx.getBean("hibernatenotificationDao");
	  	}
	  	catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	   if(notificationDao == null)
	   		notificationDao = new HibernateNotificationDao();
	}    
	 
	public static void main(String[] args){
		 NotificationManagerImpl service = new NotificationManagerImpl();
		 service.initForTest();
		 service.checkSchedule();
	 }	 
}
