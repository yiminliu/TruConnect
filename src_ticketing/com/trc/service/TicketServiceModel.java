package com.trc.service;

import java.util.Collection;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketStatus;
import com.trc.exception.service.TicketServiceException;
import com.trc.user.User;

public interface TicketServiceModel {

	public void closeTicket(Ticket ticket) throws TicketServiceException;

	public int createTicket(Ticket ticket) throws TicketServiceException;

	public void updateTicket(Ticket ticket) throws TicketServiceException;

	public void deleteTicket(Ticket ticket) throws TicketServiceException;

	public void rejectTicket(Ticket ticekt) throws TicketServiceException;

	public void resolveTicket(Ticket ticket) throws TicketServiceException;

	public void reopenTicket(Ticket ticket) throws TicketServiceException;

	public Ticket getTicketById(int id) throws TicketServiceException;

	public Collection<Ticket> getTicketsByCustomer(String customerName) throws TicketServiceException;

	public Collection<Ticket> getTicketsByCreator(String creatorName) throws TicketServiceException;

	public Collection<Ticket> getTicketsByAssignee(String ownerName) throws TicketServiceException;

	public Collection<Ticket> getTicketByKeyword(String keyword) throws TicketServiceException;

	public Collection<Ticket> getTicketByStatus(TicketStatus status) throws TicketServiceException;

	public Collection<Ticket> getAllTickets() throws TicketServiceException;

	public Collection<User> getAllTicketCreators() throws TicketServiceException;

	public void updateTicketNote(TicketNote ticketNote) throws TicketServiceException;

}
