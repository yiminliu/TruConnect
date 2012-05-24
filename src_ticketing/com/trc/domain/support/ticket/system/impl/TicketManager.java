package com.trc.domain.support.ticket.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.management.TicketManagementException;
import com.trc.exception.service.TicketServiceException;
import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.system.impl.TicketService;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;


@Component
public class TicketManager{// implements TicketManagerModel {
  @Autowired
  private TicketService ticketService;


  /* *****************************************************************
   ************************ Ticket Management ************************
   * *****************************************************************
   */

  @Loggable(value = LogLevel.TRACE)
  public int createTicket(Ticket ticket) throws TicketManagementException {
    try {
      return ticketService.createTicket(ticket);
    } catch (TicketServiceException e) {
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
    try {
      ticketService.updateTicket(ticket);
    } catch (TicketServiceException e) {
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
  public List<Ticket> getTicketByOwner(String ownerName) throws TicketManagementException {
    try {
      List<Ticket> ticketList = ticketService.getTicketsByOwner(ownerName);
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
      List<Ticket> ticketList = ticketService.getTicketByStatus(status);
      return ticketList;
    } catch (TicketServiceException e) {
      throw new TicketManagementException(e.getMessage(), e.getCause());
    }
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