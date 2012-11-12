package com.trc.manager;

import java.util.Collection;

import com.trc.domain.ticket.Ticket;
import com.trc.exception.EmailException;
import com.trc.exception.management.SupportManagementException;
import com.trc.user.User;

public interface TicketManager {

  public int createTicket(Ticket ticket) throws SupportManagementException;
  
  public void deleteTicket(Ticket ticket) throws SupportManagementException;
  
  public void updateTicket(Ticket ticket) throws SupportManagementException;
  
  public Collection<Ticket> getAllTickets() throws SupportManagementException;
  
  public Ticket getTicketById(int ticketId) throws SupportManagementException;
  
  public Collection<Ticket> getTicketByStatus(Enum status) throws SupportManagementException;

  public Collection<Ticket> getTicketByCustomer(String customerName) throws SupportManagementException; 
  
  public Collection<Ticket> getTicketByCreator(String creatorName) throws SupportManagementException;
  
  public Collection<Ticket> getTicketByAssignee(String assigneeName) throws SupportManagementException;
  
  public Collection<User> getAllTicketCreators() throws SupportManagementException;
  
  public Collection<Ticket> getTicketByKeyword(String keyword) throws SupportManagementException;
  
  public void sendEmailToAssignee(String recipientEmail, int ticketId) throws EmailException;

}