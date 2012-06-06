package com.trc.domain.support.ticket.system.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketCategory;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketPriority;
import com.trc.domain.support.ticket.system.TicketServiceModel;
import com.trc.exception.service.CouponServiceException;
import com.trc.exception.service.TicketServiceException;
import com.trc.user.User;


@Service
public class TicketService implements TicketServiceModel {

  @Autowired
  HibernateTicketDao ticketDao;
 
  /********************************************************************/
  /************************ Ticket Operations *************************/
  /********************************************************************/
  
  @Override
  public int createTicket(Ticket ticket) throws TicketServiceException{
	 try { 
        return ticketDao.createTicket(ticket);
	 } 
	 catch (DataAccessException e) {
 		throw new TicketServiceException("Error creating ticket from DAO layer: " + e.getMessage());
 	}
  }

  @Override
  public void updateTicket(Ticket ticket) throws TicketServiceException{
	  ticketDao.updateTicket(ticket);
  }
 
  @Override
  public void closeTicket(Ticket ticket) throws TicketServiceException{
	try {  
        ticketDao.closeTicket(ticket);
	} 
	catch (DataAccessException e) {
		throw new TicketServiceException("Error closing ticket from DAO layer: " + e.getMessage());
	}
  }
  
  @Override
  public void deleteTicket(Ticket ticket) throws TicketServiceException{
	  ticketDao.deleteTicket(ticket);
  }
    
  public List<Ticket> getAllTickets()throws TicketServiceException{
	  return ticketDao.getAllTickets();
  }  
  
  @Override
  public void rejectTicket(Ticket ticket) throws TicketServiceException{
    ticketDao.rejectTicket(ticket);
    
  }
  
  @Override
  public void resolveTicket(Ticket ticket) throws TicketServiceException{
     ticketDao.resolveTicket(ticket);    
  }

  @Override
  public Ticket getTicketById(int id)throws TicketServiceException{	  
	  return ticketDao.searchTicketById(id);
  }
  
  @Override
  public List<Ticket> getTicketsByCustomer(String customerName) throws TicketServiceException{
     return ticketDao.searchTicketByCustomer(customerName);
  }
  
  @Override
  public List<Ticket> getTicketsByCreator(String creatorName) throws TicketServiceException{
     return ticketDao.searchTicketByCreator(creatorName);
  }
  
  @Override
  public List<Ticket> getTicketsByAssignee(String assigneeName) throws TicketServiceException{
	  return ticketDao.searchTicketByAssignee(assigneeName);
  }
  
  @Override
  public List<Ticket> getTicketByKeyword(String keyword)throws TicketServiceException{
	  return ticketDao.searchTicketByKeyword(keyword);
  }
  
  @Override
  public List<Ticket> getTicketByStatus(Enum status)throws TicketServiceException{
	  return ticketDao.searchTicketByStatus(status);
  }
  
  @Override
  public void reopenTicket(Ticket ticket) throws TicketServiceException{
     ticketDao.reopenTicket(ticket);    
  }
  
  /********************************************************************/
  /************************ TicketNote Operations *************************/
  /********************************************************************/
  
  @Override
  public void updateTicketNote(TicketNote ticketNote)throws TicketServiceException{
	  ticketDao.updateTicketNote(ticketNote);	  
  }
  
  private void initForTest() {
  	
  	ApplicationContext appCtx = new ClassPathXmlApplicationContext("application-context.xml");
  	
   	ticketDao = (HibernateTicketDao)appCtx.getBean("ticketDao");
   	if(ticketDao == null)
   		ticketDao = new HibernateTicketDao();
  }    
    
  
  public static void main(String[] args) { 
  	    	  	
  	TicketService ts = new TicketService();
  	ts.initForTest();
  	
      try{
    
    	 // TicketNote tn = new TicketNote();
    	 // User owner = new User();
    	 // owner.setUserId(547);
    	  
    	 // User customer = new User();
    	 // customer.setUserId(547);
    	 // tn.setNote("test");
    	     	  
    	 // Ticket ticket = new Ticket();
    	 // ticket.setCategory(TicketCategory.ASSIGNED);
    	 // ticket.setPriority(TicketPriority.NORMAL);
    	 // ticket.setStatus(TicketStatus.OPEN);
    	//  ticket.addTicketNote(tn);
    	 // ticket.setOwner(owner);
    	 // ticket.setCustomer(customer);
    	  
    	 // ts.createTicket(ticket);
    	
    	  
    	  Ticket ticket = ts.getTicketById(34);
    	 // System.out.println("ticket notes = "+ ticket.getNotes().get(0).getNote());
    	  Collection collection = ticket.getNotes(); 
    	  System.out.println("ticket notes = ");
    	  Iterator it = collection.iterator();
    	  while(it.hasNext()){
    	  System.out.println(((TicketNote)it.next()).getNote());
    	  }
    	 // ts.rejectTicket(ticket);
    	  
    	 // ticket = ts.getTicketById(3);
    	  
    	  //ts.resolveTicket(ticket);
    	  
    	 // List<Ticket> ticketList = ts.getAllTickets();
    	  
    	  //List<Ticket> ticketList2 = ts.getTicketsByOwner("yimin1");
    	  //List<Ticket> ticketList3 = ts.getTicketsByCustomer("yimin1");
    	 // List<Ticket> ticketList4 = ts.getTicketByKeyword("yimin");
    	  
          //	List<SMSMessage> list = ds.getSMSMessageList(AlertAction.MESSAGE_TYPE_PROM_CAPABILITY);
    	  
    	  System.out.println("ticket = "+ ticket.toString());
    	  
  	     // System.out.println("list size = "+ ticketList.size());
  	      //System.out.println("list2 size = "+ ticketList2.size());
  	      //System.out.println("list3 size = "+ ticketList3.size());
  	      //System.out.println("list4 size = "+ ticketList4.size());
  	      
  	      //for(Ticket t : ticketList){
  	    //	System.out.println(t.toString());
  	     // }
  	      
  	}
	    catch(Exception e){
		    e.printStackTrace();
		    System.out.println("Error occured while creating connection, due to : " + e.getMessage());
	    }	   
	}    

}
