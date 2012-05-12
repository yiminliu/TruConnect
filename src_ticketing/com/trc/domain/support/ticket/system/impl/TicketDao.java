package com.trc.domain.support.ticket.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketStatus;
import com.trc.domain.support.ticket.system.TicketDaoModel;
import com.trc.user.User;


@Repository
public class TicketDao extends HibernateDaoSupport implements TicketDaoModel{

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
	 setHibernateTemplate(hibernateTemplate);
  }

  /***************  Ticket Operations ******************/
  
  @Override
  @Transactional
  public int createTicket(Ticket ticket) {
	 return (Integer) getHibernateTemplate().save(ticket);
  }

  @Override
  @Transactional
  public void deleteTicket(Ticket ticket) {
    getHibernateTemplate().delete(ticket);
  }

  @Override
  @Transactional
  public void updateTicket(Ticket ticket) {
    getHibernateTemplate().update(ticket);
  }
  
  @Override
  @Transactional
  public List<Ticket> getAllTickets(){
	  return getHibernateTemplate().find("from Ticket");
  }
  
  @Override
  @Transactional
  public List<Ticket> getAllOpenTickets(){
	  return getHibernateTemplate().find("from Ticket t where t.status = 'OPEN'");
  }
  
  @Override
  @Transactional
  public void closeTicket(Ticket ticket){
	  ticket.setStatus(TicketStatus.CLOSED);
	  updateTicket(ticket);
  }
  
  @Override
  @Transactional
  public void resolveTicket(Ticket ticket){
	  if(ticket == null) 
	     throw new IllegalArgumentException("Ticket cannot be null while call resolveTicket()"); 
	  ticket.setStatus(TicketStatus.RESOLVED);
	  updateTicket(ticket);
  }
  
  @Override
  @Transactional
  public void rejectTicket(Ticket ticket){
	  ticket.setStatus(TicketStatus.REJECTED);
	  updateTicket(ticket);
  }
    
  @Override
  @Transactional
  public void reopenTicket(Ticket ticket) {
	 ticket.setStatus(TicketStatus.OPEN); 
	 updateTicket(ticket);
  }
  
  @Override
  @Transactional
  public Ticket searchTicketById(int ticketId) {
      return getHibernateTemplate().get(Ticket.class, ticketId);
  }
  
  @Override
  @Transactional
  public List<Ticket> searchTicketByTitle(String title) {
	 return  getHibernateTemplate().find("from Ticket t where t.title = ?", title);
  }

  @Override
  @Transactional
  public List<Ticket> searchTicketByCustomer(String customerName) {
	  HibernateTemplate ht = getHibernateTemplate();
	  
	  List<User> userList = ht.find("from User u where u.username = ?", customerName);	 
	  
	  if(userList != null && userList.size() > 0) {
		 User customer = (User)userList.get(0);
		 int userId = customer.getUserId();
		 return  ht.find("from Ticket t where t.owner.userId = ?" , userId);
	  }	  
	  return null;
	  
  }

  @Override
  @Transactional
  public List<Ticket> searchTicketByOwner(String ownerName) {
	  HibernateTemplate ht = getHibernateTemplate();
	  	  
	  List<User> userList = ht.find("from User u where u.username = ?", ownerName);	  
	  
	  if(userList != null && userList.size() > 0) {
		 User owner = (User)userList.get(0);
	     int userId = owner.getUserId();
	     return  ht.find("from Ticket t where t.owner.userId = ?" , userId);
	  }	  
	  return null;
  }

  @Override
  @Transactional
  public List<Ticket> searchTicketByKeyword(String keyword) {
	  
	  HibernateTemplate ht = getHibernateTemplate(); 
	  
	  List<Ticket> resultList = new ArrayList<Ticket>();
	  
	  List<Ticket> titleList  =  ht.find("from Ticket t where t.title = ?", keyword);
	  List<Ticket> ownerList  = searchTicketByOwner(keyword);
	  List<Ticket> custList   = searchTicketByCustomer(keyword);
	  List<Ticket> noteList   =  ht.find("from Ticket t where t.notes.note like ?", "%" + keyword + "%");
		 
	  resultList.addAll(titleList);
	  resultList.addAll(ownerList);
	  resultList.addAll(custList);
	  resultList.addAll(noteList);
	  
	  return resultList;
  }

/***************  TicketNote Operations ******************/
  
  @Transactional
  public int createTicketNote(TicketNote ticketNote) {
	 return (Integer) getHibernateTemplate().save(ticketNote);
  }
}