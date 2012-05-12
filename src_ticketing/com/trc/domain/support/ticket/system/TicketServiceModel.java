package com.trc.domain.support.ticket.system;

import java.util.Collection;
import java.util.List;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketCategory;
import com.trc.domain.support.ticket.TicketStatus;

public interface TicketServiceModel {

  public void closeTicket(Ticket ticket);

  public int createTicket(Ticket ticket);
  
  public void updateTicket(Ticket ticket);
  
  public void deleteTicket(Ticket ticket);

  public void rejectTicket(Ticket ticekt);

  public void resolveTicket(Ticket ticket);
  
  public List<Ticket> getAllOpenTickets();
  
  public void reopenTicket(Ticket ticket);
  
  public Ticket getTicketById(int id);

  public List<Ticket> getTicketsByCustomer(String customerName);

  public List<Ticket> getTicketsByOwner(String ownerName);
  
  public List<Ticket> getTicketByKeyword(String keyword);

}
