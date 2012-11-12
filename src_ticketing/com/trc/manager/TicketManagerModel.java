package com.trc.manager;

import java.util.Collection;

import com.trc.domain.ticket.Ticket;

public interface TicketManagerModel {

  public void closeTicket(Ticket ticket);

  public void createTicket(Ticket ticket);

  public Collection<Ticket> getTicketsByCustomer(int custId);

  public Collection<Ticket> getTicketsByOwner(int ownerId);

  public void rejectTicket(Ticket ticekt);

  public void resolveTicket(Ticket ticket);

}