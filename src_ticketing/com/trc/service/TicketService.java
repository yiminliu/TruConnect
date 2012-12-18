package com.trc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trc.dao.TicketDao;
import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketStatus;
import com.trc.exception.service.TicketServiceException;
import com.trc.user.User;

@Service
public class TicketService implements TicketServiceModel {

	@Autowired
	TicketDao ticketDao;

	/********************************************************************/
	/************************ Ticket Operations *************************/
	/********************************************************************/

	@Override
	@Transactional
	public int createTicket(Ticket ticket) throws TicketServiceException {
		try {
			return ticketDao.createTicket(ticket);
		} catch (DataAccessException e) {
			throw new TicketServiceException("Error creating ticket from DAO layer: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void updateTicket(Ticket ticket) throws TicketServiceException {
		ticketDao.updateTicket(ticket);
	}

	@Override
	@Transactional
	public void closeTicket(Ticket ticket) throws TicketServiceException {
		try {
			ticketDao.closeTicket(ticket);
		} catch (DataAccessException e) {
			throw new TicketServiceException("Error closing ticket from DAO layer: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void deleteTicket(Ticket ticket) throws TicketServiceException {
		ticketDao.deleteTicket(ticket);
	}

	@Transactional(readOnly = true)
	public List<Ticket> getAllTickets() throws TicketServiceException {
		return ticketDao.getAllTickets();
	}

	@Override
	@Transactional
	public void rejectTicket(Ticket ticket) throws TicketServiceException {
		ticketDao.rejectTicket(ticket);

	}

	@Override
	@Transactional
	public void resolveTicket(Ticket ticket) throws TicketServiceException {
		ticketDao.resolveTicket(ticket);
	}

	@Override
	@Transactional(readOnly = true)
	public Ticket getTicketById(int id) throws TicketServiceException {
		return ticketDao.searchTicketById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> getTicketsByCustomer(String customerName) throws TicketServiceException {
		return ticketDao.searchTicketByCustomer(customerName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> getTicketsByCreator(String creatorName) throws TicketServiceException {
		return ticketDao.searchTicketByCreator(creatorName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> getTicketsByAssignee(String assigneeName) throws TicketServiceException {
		return ticketDao.searchTicketByAssignee(assigneeName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> getTicketByKeyword(String keyword) throws TicketServiceException {
		return ticketDao.searchTicketByKeyword(keyword);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> getTicketByStatus(TicketStatus status) throws TicketServiceException {
		return ticketDao.searchTicketByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllTicketCreators() throws TicketServiceException {
		return ticketDao.getAllCreators();
	}

	@Override
	@Transactional
	public void reopenTicket(Ticket ticket) throws TicketServiceException {
		ticketDao.reopenTicket(ticket);
	}

	/********************************************************************/
	/************************ TicketNote Operations *************************/
	/********************************************************************/

	@Override
	@Transactional
	public void updateTicketNote(TicketNote ticketNote) throws TicketServiceException {
		ticketDao.updateTicketNote(ticketNote);
	}

	private void initForTest() {

		ApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context.xml");

		ticketDao = (TicketDao) appCtx.getBean("ticketDao");
		if (ticketDao == null)
			ticketDao = new TicketDao();
	}

}
