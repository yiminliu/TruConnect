package com.trc.manager;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.trc.exception.EmailException;
import com.trc.exception.management.SupportManagementException;
import com.trc.exception.service.TicketServiceException;
import com.trc.manager.UserManager;
import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketStatus;
import com.trc.service.email.VelocityEmailService;
import com.trc.service.ticket.TicketServiceImpl;
import com.trc.user.User;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;


@Component
public class TicketManagerImpl implements TicketManager {
	  	
  @Autowired
  private TicketServiceImpl ticketService;
  @Autowired
  private UserManager userManager;
  @Autowired
  private VelocityEmailService velocityEmailService;
  
  
  /* *****************************************************************
   ************************ Ticket Management ************************
   * *****************************************************************/

  @Override
  @Loggable(value = LogLevel.TRACE)
  public int createTicket(Ticket ticket) throws SupportManagementException {
	 User customer = null;
	 User assignee = null;
     try {
    	if(ticket != null && ticket.getCustomer() != null)
    	   customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
    	if(ticket != null && ticket.getAssignee() == null)
    		assignee = userManager.getUserByUsername("yiminliu");		
    	else
            assignee = userManager.getUserByUsername(ticket.getAssignee().getUsername());    		
    	ticket.setCustomer(customer);
		ticket.setAssignee(assignee);
 	    ticket.setCreator(userManager.getLoggedInUser());	    	    		
		ticket.setStatus(TicketStatus.OPEN);
        return ticketService.createTicket(ticket);
    } 
    catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public void deleteTicket(Ticket ticket) throws SupportManagementException {
    try {
      ticketService.deleteTicket(ticket);
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public void updateTicket(Ticket ticket) throws SupportManagementException {
	 User customer = null;
   	 User assignee = null;
   	 List<TicketNote> notes = (List<TicketNote>)ticket.getNotes();
     try {
      	 if(ticket != null && ticket.getCustomer() != null && ticket.getCustomer().getEmail() == null) {	
		    customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
		    ticket.setCustomer(customer);
	     }   
	     if(ticket != null && ticket.getAssignee() != null && ticket.getAssignee().getEmail() == null) {	
	        assignee = userManager.getUserByUsername(ticket.getAssignee().getUsername());
			ticket.setAssignee(assignee);
		 }   	    
	     if(notes != null && notes.size() > 0){
            TicketNote note = notes.get(0);
	        note.setAuthor(userManager.getLoggedInUser());
		    note.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	        ticket.addNote(note);		  
	     }   
	     ticketService.updateTicket(ticket);
    } 
    catch (TicketServiceException e) {
       throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }   
    
  @Override
  @Loggable(value = LogLevel.TRACE)
  public Ticket getTicketById(int ticketId) throws SupportManagementException {
    try {
      return ticketService.getTicketById(ticketId);
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getAllTickets() throws SupportManagementException {
    try {
      return ticketService.getAllTickets();
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByCustomer(String customerName) throws SupportManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByCustomer(customerName);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByCreator(String creatorName) throws SupportManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByCreator(creatorName);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByAssignee(String assigneeName) throws SupportManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByAssignee(assigneeName);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByKeyword(String keyword) throws SupportManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketByKeyword(keyword);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByStatus(Enum status) throws SupportManagementException {
    try {
      return ticketService.getTicketByStatus(status);
    } 
    catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<User> getAllTicketCreators() throws SupportManagementException {
	List<User> creators = null;
	try {
       creators = ticketService.getAllTicketCreators();      
    } catch (TicketServiceException e) {
      throw new SupportManagementException(e.getMessage(), e.getCause());
    }
    return creators;
  }
  
  
  /* *****************************************************************
   *********************** Utility Methods  **************************
   * *****************************************************************/
     
  @Override
  @Loggable(value = LogLevel.TRACE)
  public void sendEmailToAssignee(String recipientEmail, int ticketId) throws EmailException {
    
      SimpleMailMessage myMessage = new SimpleMailMessage();
      myMessage.setTo(recipientEmail);
      myMessage.setFrom("no-reply@truconnect.com");
      myMessage.setSubject("Ticket# "+ ticketId + " Has Been Assigned to You");
      Map<Object, Object> mailModel = new HashMap<Object, Object>();
      mailModel.put("ticketId", ticketId);
      velocityEmailService.send("ticket", myMessage, mailModel);
  }
}