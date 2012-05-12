package com.trc.domain.support.ticket.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketCategory;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketStatus;
import com.trc.domain.support.ticket.system.TicketServiceModel;
import com.trc.user.User;


@Service
public class TicketService implements TicketServiceModel {

  @Autowired
  TicketDao ticketDao;
  
  @Override
  public void closeTicket(Ticket ticket) {
    ticketDao.closeTicket(ticket);
    
  }

  @Override
  public int createTicket(Ticket ticket) {
     return ticketDao.createTicket(ticket);
  }

  @Override
  public void updateTicket(Ticket ticket) {
	  ticketDao.updateTicket(ticket);
  }
  
  @Override
  public void deleteTicket(Ticket ticket) {
	  ticketDao.deleteTicket(ticket);
  }
    
  public List<Ticket> getAllTickets(){
	  return ticketDao.getAllTickets();
  }
  
  public List<Ticket> getAllOpenTickets(){
	  return ticketDao.getAllOpenTickets();
  }
  
  @Override
  public void rejectTicket(Ticket ticket) {
    ticketDao.rejectTicket(ticket);
    
  }
  
  @Override
  public void resolveTicket(Ticket ticket) {
     ticketDao.resolveTicket(ticket);    
  }

  @Override
  public Ticket getTicketById(int id){	  
	  return ticketDao.searchTicketById(id);
  }
  
  @Override
  public List<Ticket> getTicketsByCustomer(String customerName) {
     return ticketDao.searchTicketByCustomer(customerName);
  }
  
  @Override
  public List<Ticket> getTicketsByOwner(String ownerName) {
	  return ticketDao.searchTicketByCustomer(ownerName);
  }
  
  @Override
  public List<Ticket> getTicketByKeyword(String keyword){
	  return ticketDao.searchTicketByKeyword(keyword);
  }
  
  @Override
  public void reopenTicket(Ticket ticket) {
     ticketDao.reopenTicket(ticket);    
  }
  
  private void initForTest() {
  	
  	ApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context.xml");
   	ticketDao = (TicketDao)appCtx.getBean("ticketDao");
  }    
  
  public static void main(String[] args) { 
  	    	  	
  	TicketService ts = new TicketService();
  	ts.initForTest();
  	
      try{
    
    	  TicketNote tn = new TicketNote();
    	  User owner = new User();
    	  owner.setUserId(547);
    	  
    	  User customer = new User();
    	  customer.setUserId(547);
    	  List<TicketNote> list = new ArrayList<TicketNote>();
    	  list.add(tn);
    	  tn.setNote("test");
    	  tn.setTicketNoteId(tn.getTicketNoteId());
    	  
    	  Ticket ticket = new Ticket();
    	  ticket.setCategory(TicketCategory.ASSIGNED);
    	  ticket.setImportance(1);
    	  ticket.setStatus(TicketStatus.OPEN);
    	  ticket.setNotes(list);
    	  ticket.setOwner(owner);
    	  ticket.setCustomer(customer);
    	  
    	  ts.createTicket(ticket);
    	
    	  
    	  ticket = ts.getTicketById(2);
    	  ts.rejectTicket(ticket);
    	  
    	  ticket = ts.getTicketById(3);
    	  
    	  ts.resolveTicket(ticket);
    	  
    	  List<Ticket> ticketList = ts.getAllTickets();
    	  
    	  List<Ticket> ticketList2 = ts.getTicketsByOwner("yimin1");
    	  List<Ticket> ticketList3 = ts.getTicketsByCustomer("yimin1");
    	  //List<Ticket> ticketList4 = ts.getTicketByKeyword("yimin");
    	  
          //	List<SMSMessage> list = ds.getSMSMessageList(AlertAction.MESSAGE_TYPE_PROM_CAPABILITY);
  	      System.out.println("list size = "+ ticketList.size());
  	      System.out.println("list2 size = "+ ticketList2.size());
  	      System.out.println("list3 size = "+ ticketList3.size());
  	      //System.out.println("list4 size = "+ ticketList4.size());
  	      
  	      for(Ticket t : ticketList){
  	    	System.out.println(t.toString());
  	      }
  	      
  	}
	    catch(Exception e){
		    e.printStackTrace();
		    System.out.println("Error occured while creating connection, due to : " + e.getMessage());
	    }	   
	}    

}
