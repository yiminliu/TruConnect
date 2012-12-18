package com.trc.dao;

import java.util.Collection;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketStatus;
import com.trc.user.User;

public interface TicketDaoModel {

	public int createTicket(Ticket ticket);

	public void deleteTicket(Ticket ticket);

	public void updateTicket(Ticket ticket);

	public Collection<Ticket> getAllTickets();

	public Collection<User> getAllCreators();

	public Ticket searchTicketById(int ticketId);

	public Collection<Ticket> searchTicketByTitle(String title);

	public Collection<Ticket> searchTicketByCustomer(String customerName);

	public Collection<Ticket> searchTicketByCreator(String creatorName);

	public Collection<Ticket> searchTicketByAssignee(String assigneeName);

	public Collection<Ticket> searchTicketByKeyword(String keyword);

	public Collection<Ticket> searchTicketByStatus(TicketStatus status);

	public void reopenTicket(Ticket ticket);

	public void closeTicket(Ticket ticket);

	public void resolveTicket(Ticket ticket);

	public void rejectTicket(Ticket ticket);
}
