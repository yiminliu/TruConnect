package com.trc.dao;

import java.util.List;

import com.trc.domain.ticket.Ticket;
import com.trc.user.User;

public interface TicketDao {

  public int createTicket(Ticket ticket);

  public void deleteTicket(Ticket ticket);

  public void updateTicket(Ticket ticket);
  
  public List<Ticket> getAllTickets();
  
  public List<User> getAllCreators();
    
  public Ticket searchTicketById(int ticketId);
  
  public List<Ticket> searchTicketByTitle(String title);
  
  public List<Ticket> searchTicketByCustomer(String customerName);
  
  public List<Ticket> searchTicketByCreator(String creatorName);
  
  public List<Ticket> searchTicketByAssignee(String assigneeName);  
  
  public List<Ticket> searchTicketByKeyword(String keyword);
  
  public List<Ticket> searchTicketByStatus(Enum status);
  
  public void reopenTicket(Ticket ticket);
  
  public void closeTicket(Ticket ticket);
  
  public void resolveTicket(Ticket ticket);
  
  public void rejectTicket(Ticket ticket);
}
