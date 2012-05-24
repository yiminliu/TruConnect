package com.trc.domain.support.ticket.system;

import java.util.List;

import com.trc.domain.support.ticket.Ticket;

public interface TicketDaoModel {

  public int createTicket(Ticket ticket);

  public void deleteTicket(Ticket ticket);

  public void updateTicket(Ticket ticket);
  
  public List<Ticket> getAllTickets();
    
  public Ticket searchTicketById(int ticketId);
  
  public List<Ticket> searchTicketByTitle(String title);
  
  public List<Ticket> searchTicketByCustomer(String customerName);
  
  public List<Ticket> searchTicketByOwner(String ownerName);  
  
  public List<Ticket> searchTicketByKeyword(String keyword);
  
  public List<Ticket> searchTicketByStatus(Enum status);
  
  public void reopenTicket(Ticket ticket);
  
  public void closeTicket(Ticket ticket);
  
  public void resolveTicket(Ticket ticket);
  
  public void rejectTicket(Ticket ticket);
}
