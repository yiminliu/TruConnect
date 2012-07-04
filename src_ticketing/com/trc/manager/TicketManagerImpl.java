package com.trc.manager;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.management.TicketManagementException;
import com.trc.exception.service.TicketServiceException;
import com.trc.manager.UserManager;
import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketStatus;
import com.trc.service.ticket.TicketServiceImpl;
import com.trc.user.User;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;


@Component
public class TicketManagerImpl{// implements TicketManagerModel {
  @Autowired
  private TicketServiceImpl ticketService;
  @Autowired
  private UserManager userManager;

  /* *****************************************************************
   ************************ Ticket Management ************************
   * *****************************************************************
   */

  @Loggable(value = LogLevel.TRACE)
  public int createTicket(Ticket ticket) throws TicketManagementException {
	 User customer = null;
	 User assignee = null;
     try {
    	if(ticket != null && ticket.getCustomer() != null)
    	   customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
    	//if(ticket != null && ticket.getAssignee() != null)
		//   assignee = userManager.getUserByUsername(ticket.getAssignee().getUsername());
    	assignee = userManager.getUserByUsername("yiminliu");		
    	ticket.setCustomer(customer);
		ticket.setAssignee(assignee);
 	    ticket.setCreator(userManager.getLoggedInUser());	    	    		
		ticket.setStatus(TicketStatus.OPEN);
        return ticketService.createTicket(ticket);
    } 
    catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void deleteTicket(Ticket ticket) throws TicketManagementException {
    try {
      ticketService.deleteTicket(ticket);
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void updateTicket(Ticket ticket) throws TicketManagementException {
	 User customer = null;
   	 User assignee = null;
   	 List<TicketNote> notes = (List)ticket.getNotes();
     try {
      	 if(ticket != null && ticket.getCustomer() != null) {	
		    customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
		    ticket.setCustomer(customer);
	     }   
	     if(ticket != null && ticket.getAssignee() != null) {	
	        assignee = userManager.getUserByUsername(ticket.getAssignee().getUsername());
			ticket.setAssignee(assignee);
		 }   
	     /*
	     for(TicketNote ticketNote : Collections.synchronizedCollection(ticketNotes)) {	
	         if(ticketNote.getNote() != null && ticketNote.getNote().length() > 0) {
				    ticketNote.setAuthor(userManager.getLoggedInUser());
				    ticketNote.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			        //ticketNote.setTicket(ticket);
				    //ticket.addTicketNote(ticketNote);
				    
	    	 }   
		 } */
	     if(notes != null && notes.size() > 0){
            TicketNote note = notes.get(notes.size()-1);
	        note.setAuthor(userManager.getLoggedInUser());
		    note.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	        //ticketNote.setTicket(ticket);
		    ticket.addNote(note);	       
	     }   
	     ticketService.updateTicket(ticket);
    } 
    catch (TicketServiceException e) {
       throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public void rejectTicket(Ticket ticket) throws TicketManagementException {
    try {
      ticketService.rejectTicket(ticket);
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public void resolveTicket(Ticket ticket) throws TicketManagementException {
    try {
      ticketService.resolveTicket(ticket);
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
    
  @Loggable(value = LogLevel.TRACE)
  public void reopenTicket(Ticket ticket) throws TicketManagementException {
     try {
        ticketService.reopenTicket(ticket);
     } catch (TicketServiceException e) {
       throw new TicketManagementException(e.getMessage(), e.getCause());
     }
   }

  @Loggable(value = LogLevel.TRACE)
  public Ticket getTicketById(int ticketId) throws TicketManagementException {
    try {
      return ticketService.getTicketById(ticketId);
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getAllTickets() throws TicketManagementException {
    try {
      return ticketService.getAllTickets();
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByCustomer(String customerName) throws TicketManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByCustomer(customerName);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByCreator(String creatorName) throws TicketManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByCreator(creatorName);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByAssignee(String assigneeName) throws TicketManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByAssignee(assigneeName);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByKeyword(String keyword) throws TicketManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketByKeyword(keyword);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public List<Ticket> getTicketByStatus(Enum status) throws TicketManagementException {
    try {
      return ticketService.getTicketByStatus(status);
    } 
    catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
  }
  
  @Loggable(value = LogLevel.TRACE)
  public List<User> getAllTicketCreators() throws TicketManagementException {
	List<User> creators = null;
	try {
       creators = ticketService.getAllTicketCreators();      
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
    return creators;
  }

  /* *****************************************************************
   *********************** Ticket Note Management ********************
   * *****************************************************************
   */

  @Loggable(value = LogLevel.TRACE)
  public void updateTicketNote(Ticket ticket)throws TicketManagementException {
	  try{
		 List<TicketNote> ticketList = (List<TicketNote>)ticket.getNotes();
		 if (ticketList != null && ticketList.size() > 1) {
  		     TicketNote ticketNote = ticketList.get(1);
  		     ticketNote.setTicket(ticket);
  		     ticketService.updateTicketNote(ticketNote);
		 }  		     
	  }
	  catch (TicketServiceException e) {
	      throw new TicketManagementException(e.getMessage(), e.getCause());
	  }
  }
}