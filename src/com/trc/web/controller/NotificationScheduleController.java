package com.trc.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.domain.support.notification.NotificationMessage;
import com.trc.domain.support.notification.NotificationPage;
import com.trc.domain.support.notification.NotificationSchedule;
import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketNote;
import com.trc.exception.management.SupportManagementException;
import com.trc.manager.NotificationManager;
import com.trc.manager.NotificationManagerImpl;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("admin/notification")
public class NotificationScheduleController {

	@Autowired
	NotificationManager notificationManager;
		
	@RequestMapping(value="/showNotification/all", method=RequestMethod.GET)
	public String showNotificationSchedules(Model model){
		model.addAttribute("notificationList", notificationManager.getAllScheduledNotifications());
		return "admin/notification/showNotificationSchedules";		
	}
	
	@RequestMapping(value="/showNotification/{notificationId}", method=RequestMethod.GET)
	public String showNotification(@PathVariable("notificationId") int notificationId, Model model){
		NotificationSchedule ns = notificationManager.getNotificationById(notificationId);
		model.addAttribute("notification", ns);
		return "admin/notification/showNotificationDetail";		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/scheduleNotification", method=RequestMethod.GET)
	public String notificationForm(Model model){
		model.addAttribute(new NotificationSchedule());
		return "admin/notification/scheduleNotification";		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value={"", "/scheduleNotification"}, method=RequestMethod.POST)
	public ModelAndView processNotificationForm(@ModelAttribute NotificationSchedule notificationSchedule, BindingResult result){
		ResultModel resultModel = new ResultModel("admin/notification/scheduleNotificationSuccess");
		notificationManager.saveNotificationSchedule(notificationSchedule);   
		return resultModel.getSuccess();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/scheduleNotification/{messageId}", method=RequestMethod.GET)
	public String notificationFormWithMessage(Model model, @PathVariable int messageId){
		NotificationMessage notificationMessage = notificationManager.getNotificationMessageById(messageId);
		model.addAttribute(new NotificationSchedule());
		model.addAttribute("singleMessage", notificationMessage);		
		return "admin/notification/scheduleNotification";		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createNotificationMessage", method=RequestMethod.GET)
	public String notificationMessageForm(Model model){
		model.addAttribute(new NotificationMessage());
		return "admin/notification/createNotificationMessage";		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createNotificationMessage", method=RequestMethod.POST)
	public ModelAndView processNotificationMessageForm(@ModelAttribute NotificationMessage notificationMessage, BindingResult result){
		ResultModel resultModel = new ResultModel("admin/notification/createNotificationMessageSuccess");
		Integer messageId = notificationManager.saveNotificationMessage(notificationMessage); 
		notificationMessage.setId(messageId);
		resultModel.addObject("notificationMessage", notificationMessage);
		return resultModel.getSuccess();			
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createNotificationPage", method=RequestMethod.GET)
	public String notificationPageForm(Model model){
		model.addAttribute(new NotificationPage());
		return "admin/notification/createNotificationPage";		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createNotificationPage", method=RequestMethod.POST)
	public ModelAndView processNotificationPageForm(@ModelAttribute NotificationPage notificationPage, BindingResult result){
		ResultModel resultModel = new ResultModel("admin/notification/scheduleNotification");
		Integer messageId = notificationManager.saveNotificationPage(notificationPage); 
		//resultModel.addObject("notificationPage", notificationPage);
		resultModel.addObject("notificationSchedule", new NotificationSchedule());
		return resultModel.getSuccess();			
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/deleteNotification/{notificationId}", method=RequestMethod.GET)
	public String deleteNotification(@PathVariable("notificationId") int notificationId, Model model){
		notificationManager.deleteNotification(notificationId);
		model.addAttribute("notificationList", notificationManager.getAllScheduledNotifications());
		return "admin/notification/showNotificationSchedules";		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/updateNotification/{notificationId}", method=RequestMethod.GET)
	public String updateNotification(@PathVariable("notificationId") int notificationId, Model model){
		//Ticket ticket = null;
        //int numOfNotes = 0;
		//try{
		//	ticket = ticketManager.getTicketById(ticketId);
		//}	    
		//catch(TicketManagementException te){
		//	throw new RuntimeException(te);
		//}
		//if(ticket != null) {
		//	Collection<TicketNote> notes = ticket.getNotes();
		//    numOfNotes = notes.size();
	    //}		
		model.addAttribute("notification", notificationManager.getNotificationById(notificationId));
		//model.addAttribute("numOfNotes", numOfNotes);		
		//notificationManager.deleteNotification(notificationId);
		//model.addAttribute("notificationList", notificationManager.getAllScheduledNotifications());
		return "admin/notification/updateNotification";		
	}
	
	@ModelAttribute
	private void dateReferenceData(ModelMap modelMap) {
	    modelMap.addAttribute("months", Config.months.entrySet());
	    modelMap.addAttribute("years", Config.yearsFuture.entrySet());
	    int numDaysInMonth = new DateTime().dayOfMonth().getMaximumValue();
	    int[] days = new int[numDaysInMonth];
	    for (int i = 0; i < numDaysInMonth; i++) {
	      days[i] = i + 1;
	    }
	    modelMap.addAttribute("days", days);
	    
	    int minuteInHour = new DateTime().minuteOfHour().getMaximumValue();
	    int[] minutes = new int[minuteInHour+1];
	    for (int i = 0; i <= minuteInHour; i++) {
	         minutes[i] = i;
	    }
	  
	    modelMap.addAttribute("minutes", minutes);
	    
	    int hourInDay = new DateTime().hourOfDay().getMaximumValue();
	    int[] hours = new int[hourInDay+1];
	    for (int i = 0; i <= hourInDay; i++) {
	       	hours[i] = i;
	    }
	    modelMap.addAttribute("hours", hours);
	  }
	
	@ModelAttribute("notificationMessages")
	public Set<NotificationMessage> getNotificationMessages(){
		//if(notificationManager == null)
		//	notificationManager = new NotificationManagerImpl();	 
		return (Set<NotificationMessage>)notificationManager.getAllNotificationMessages();
	}
	
	@ModelAttribute("notificationPages")
	public Set<NotificationPage> getPages(){
		//if(notificationManager == null)
		//	notificationManager = new NotificationManagerImpl();	 
		return (Set<NotificationPage>)notificationManager.getAllNotificationPages();
	}
	
}
