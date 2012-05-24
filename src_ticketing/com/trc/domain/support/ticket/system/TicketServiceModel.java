package com.trc.domain.support.ticket.system;

import java.util.Collection;
import java.util.List;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketCategory;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketStatus;
import com.trc.exception.service.TicketServiceException;

public interface TicketServiceModel {

  public void closeTicket(Ticket ticket) throws TicketServiceException;

  public int createTicket(Ticket ticket) throws TicketServiceException;
  
  public void updateTicket(Ticket ticket) throws TicketServiceException;
  
  public void deleteTicket(Ticket ticket)throws TicketServiceException;

  public void rejectTicket(Ticket ticekt)throws TicketServiceException;

  public void resolveTicket(Ticket ticket)throws TicketServiceException;  
  
  public void reopenTicket(Ticket ticket)throws TicketServiceException;
  
  public Ticket getTicketById(int id)throws TicketServiceException;

  public List<Ticket> getTicketsByCustomer(String customerName)throws TicketServiceException;

  public List<Ticket> getTicketsByOwner(String ownerName)throws TicketServiceException;
  
  public List<Ticket> getTicketByKeyword(String keyword)throws TicketServiceException;
  
  public List<Ticket> getTicketByStatus(Enum status)throws TicketServiceException;
  
  public void updateTicketNote(TicketNote ticketNote)throws TicketServiceException;

}
