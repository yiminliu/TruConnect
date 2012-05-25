package com.trc.domain.support.ticket.system.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketStatus;
import com.trc.domain.support.ticket.system.TicketDaoModel;
import com.trc.manager.impl.UserManager;
import com.trc.user.User;

@Repository
public class TicketDao extends HibernateDaoSupport implements TicketDaoModel{

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
	 setHibernateTemplate(hibernateTemplate);
  }

  @Autowired
  UserManager userManager = new UserManager();
  
  /***************  Ticket Operations ******************/
  
  @Override
  @Transactional
  public int createTicket(Ticket ticket) {
	 validateTicket(ticket);	 
	
	 if(ticket.getStatus() == null || ticket.getStatus() != TicketStatus.OPEN)
	    ticket.setStatus(TicketStatus.OPEN);
	 
	 ticket.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	 	 	 
	 int id = (Integer) getHibernateTemplate().save(ticket);
	 
	 return id;
  }

  @Override
  @Transactional
  public void updateTicket(Ticket ticket) {
	 validateTicket(ticket);	 
     ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));	 
     getHibernateTemplate().saveOrUpdate(ticket);
  }


  @Override
  @Transactional
  public void deleteTicket(Ticket ticket) {
	 validateTicket(ticket);	
     getHibernateTemplate().delete(ticket);
  }
  
  @Override
  @Transactional(readOnly=true)
  public List<Ticket> getAllTickets(){
	  return getHibernateTemplate().find("from Ticket t order by t.id desc");
  }
    
  @Override
  @Transactional
  public void closeTicket(Ticket ticket){
	  validateTicket(ticket);
	  ticket.setStatus(TicketStatus.CLOSED);
	  ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
	  updateTicket(ticket);
  }
  
  @Override
  @Transactional
  public void resolveTicket(Ticket ticket){
	  validateTicket(ticket);
	  ticket.setStatus(TicketStatus.RESOLVED);
	  ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
	  updateTicket(ticket);
  }
  
  @Override
  @Transactional
  public void rejectTicket(Ticket ticket){
	  validateTicket(ticket);
	  ticket.setStatus(TicketStatus.REJECTED);
	  ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
	  updateTicket(ticket);
  }
    
  @Override
  @Transactional
  public void reopenTicket(Ticket ticket) {
	 validateTicket(ticket); 
	 ticket.setStatus(TicketStatus.REOPEN);
	 ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
	 updateTicket(ticket);
  }
  
  @Override
  @Transactional(readOnly=true)
  public Ticket searchTicketById(int ticketId) {
	  try{
		  //return (Ticket)getHibernateTemplate().get(Ticket.class, ticketId);
		  return (Ticket)getHibernateTemplate().find("from Ticket t where t.id = ?", ticketId).get(0);
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  return null;
	  }
  }
  
  @Override
  @Transactional(readOnly=true)
  public List<Ticket> searchTicketByTitle(String title) {
	 return  getHibernateTemplate().find("from Ticket t where t.title = ?", title);
  }

  @Override
  @Transactional(readOnly=true)
  public List<Ticket> searchTicketByCustomer(String customerName) {
     return  getHibernateTemplate().find("from Ticket t where t.customer.username like ? order by t.id desc" , wildCardKeyword(customerName));
  }

  @Override
  @Transactional(readOnly=true)
  public List<Ticket> searchTicketByOwner(String ownerName) {
	  return  getHibernateTemplate().find("from Ticket t where t.owner.username like ? order by t.id desc", wildCardKeyword(ownerName));
  }

  @Override
  @Transactional(readOnly=true)
  public List<Ticket> searchTicketByKeyword(String keyword) {
	  
	  HibernateTemplate ht = getHibernateTemplate(); 
	  
	  List<Ticket> resultList = new ArrayList<Ticket>();
	  
	  List<Ticket> titleList  = ht.find("from Ticket t where t.title like ?", wildCardKeyword(keyword));
	  List<Ticket> ownerList  = ht.find("from Ticket t where t.owner.username like ?", wildCardKeyword(keyword));//searchTicketByOwner(keyword);
	  List<Ticket> custList   = ht.find("from Ticket t where t.customer.username like ?", wildCardKeyword(keyword));//searchTicketByCustomer(keyword);
	  //List<Ticket> noteList   = ht.find("from Ticket t where t.notes.note like ?", wildCardKeyword(keyword));
		 
	  resultList.addAll(titleList);
	  resultList.addAll(ownerList);
	  resultList.addAll(custList);
	  //resultList.addAll(noteList);
	  
	  return resultList;
  }
  
  @Override
  @Transactional(readOnly=true)
  public List<Ticket> searchTicketByStatus(Enum status) {
	 return  getHibernateTemplate().find("from Ticket t where t.status = ? order by t.id desc", status);
  }
  

  private void validateTicket(Ticket ticket){
  	  if(ticket == null) 
  	     throw new IllegalArgumentException("Ticket cannot be null"); 
 }
    
/***************  TicketNote Operations ******************/
  
  @Transactional
  public int createTicketNote(TicketNote ticketNote) {
	  return (Integer) getHibernateTemplate().save(ticketNote);
  }
  
  public List<TicketNote> searchTicketNotes(Ticket ticket){
	  validateTicket(ticket);
	  int ticketId = ticket.getId();
	  return getHibernateTemplate().find("from TicketNote where ticketId = ?", ticketId);
  }
  
  public void updateTicketNote(TicketNote ticketNote){
	  if(ticketNote.getAuthor() == null){
		 ticketNote.setAuthor(userManager.getCurrentUser());
	  }
	  getHibernateTemplate().saveOrUpdate(ticketNote);
  }
  
  /***************  Utility Methods ******************/
  
  private String wildCardKeyword(String keyword){
	  if(keyword == null || keyword.equals(""))
		 return null;
	  else
		 return "%" + keyword + "%";
	 }
}