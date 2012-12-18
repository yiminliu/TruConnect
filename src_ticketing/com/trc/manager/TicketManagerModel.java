package com.trc.manager;

import java.util.Collection;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketStatus;
import com.trc.exception.EmailException;
import com.trc.exception.management.TicketManagementException;
import com.trc.user.User;

public interface TicketManagerModel {

	public int createTicket(Ticket ticket) throws TicketManagementException;

	public int customerCreateTicket(Ticket ticket) throws TicketManagementException;

	public void deleteTicket(Ticket ticket) throws TicketManagementException;

	public void updateTicket(Ticket ticket) throws TicketManagementException;

	public void customerUpdateTicket(Ticket ticket) throws TicketManagementException;

	public Collection<Ticket> getAllTickets() throws TicketManagementException;

	public Ticket getTicketById(int ticketId) throws TicketManagementException;

	public Collection<Ticket> getTicketByStatus(TicketStatus status) throws TicketManagementException;

	public Collection<Ticket> getTicketByCustomer(String customerName) throws TicketManagementException;

	public Collection<Ticket> getTicketByCreator(String creatorName) throws TicketManagementException;

	public Collection<Ticket> getTicketByAssignee(String assigneeName) throws TicketManagementException;

	public Collection<User> getAllTicketCreators() throws TicketManagementException;

	public Collection<Ticket> getTicketByKeyword(String keyword) throws TicketManagementException;

	public void sendEmailToAssignee(String recipientEmail, int ticketId) throws EmailException;

}