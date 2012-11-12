package com.trc.service.ticket;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketCategory;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketStatus;
import com.trc.exception.service.TicketServiceException;
import com.trc.user.User;

public interface TicketService {

  public void closeTicket(Ticket ticket) throws TicketServiceException;

  public int createTicket(Ticket ticket) throws TicketServiceException;
  
  public void updateTicket(Ticket ticket) throws TicketServiceException;
  
  public void deleteTicket(Ticket ticket)throws TicketServiceException;

  public void rejectTicket(Ticket ticekt)throws TicketServiceException;

  public void resolveTicket(Ticket ticket)throws TicketServiceException;  
  
  public void reopenTicket(Ticket ticket)throws TicketServiceException;
  
  public Ticket getTicketById(int id)throws TicketServiceException;

  public List<Ticket> getTicketsByCustomer(String customerName)throws TicketServiceException;
  
  public List<Ticket> getTicketsByCreator(String creatorName)throws TicketServiceException;

  public List<Ticket> getTicketsByAssignee(String ownerName)throws TicketServiceException;
  
  public List<Ticket> getTicketByKeyword(String keyword)throws TicketServiceException;
  
  public List<Ticket> getTicketByStatus(Enum status)throws TicketServiceException;
  
  public List<Ticket> getAllTickets()throws TicketServiceException;
  
  public List<User> getAllTicketCreators()throws TicketServiceException;
  
  public void updateTicketNote(TicketNote ticketNote)throws TicketServiceException;

}
