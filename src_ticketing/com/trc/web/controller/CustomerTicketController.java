package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketCategory;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketPriority;
import com.trc.domain.ticket.TicketStatus;
import com.trc.domain.ticket.TicketType;
//import com.trc.domain.support.ticket.system.impl.TicketManager;
import com.trc.exception.management.TicketManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.TicketManager;
import com.trc.manager.UserManager;
//import com.trc.manager.impl.AccountManager;
//import com.trc.manager.impl.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/ticket")
public class CustomerTicketController {

	@Autowired 
	private TicketManager ticketManager;	
	@Autowired 
	private UserManager userManager;	
	@Autowired
	private AccountManager accountManager;
	
	/**
	   * This method is used to show all tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value="/customerTicketOverview", method=RequestMethod.GET)
	public String showAllTickets(Model model){
		if(userManager.getSessionUser() != null) { // for existing customer
   		   List<Ticket> ticketList = null;
		   try{
		       ticketList = (List<Ticket>)ticketManager.getAllTickets();
		   }
		   catch(TicketManagementException tme){
			   throw new RuntimeException(tme);
		   }
		   model.addAttribute("ticketList", ticketList);
		   return "ticket/customerTicketOverview";
		}  
		else //for pre-customer
		   return "ticket/customerCreateTicket"; 
	}
	
	/**
	   * This method is used to show the form to show current logged in user's tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value="/showLoggedinCustomerTickets", method=RequestMethod.GET)
	public String showLoggedinCustomerTickets(Model model){
		List<Ticket> ticketList = null;
	    User customer = userManager.getCurrentUser();//getLoggedInUser();		
		try{
		    ticketList = (List<Ticket>)ticketManager.getTicketByCustomer(customer.getUsername());
		    /*for(Ticket ticket : ticketList){
		        notes = ticket.getNotes();
		        for(TicketNote note : notes){
		       	    if(TicketType.ADMIN.equals(note.getType()) || TicketType.AGENT.equals(note.getType()))
		     		   adminNotes.add(note);
		     	}	 
				ticket.setNotes(adminNotes);		    
			}*/			
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("customer", customer);
		model.addAttribute("ticketList", ticketList);	
		return "ticket/showCustomerTickets";
	}
	
	
	/**
	   * This method is used to show the form to create a ticket
	   * 
	   * @return ModelAndView
	   */
	@RequestMapping(value="/customerCreateTicket", method = RequestMethod.GET)
	public String customerCreateTicket(Model model){
		model.addAttribute("ticket", new Ticket());
		return "ticket/customerCreateTicket";	
	}	
	
	/**
	  * This method is used to process the ticket form to create a ticket
	  * 
	  * @return ModelAndView
	  */
	@RequestMapping(value="/customerCreateTicket", method = RequestMethod.POST)
	public ModelAndView processCustomerCreateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result){
		ResultModel resultModel = new ResultModel("ticket/customerCreateTicketSuccess");
		try {	
			if(ticket != null){
	           ticket.setType(TicketType.CUSTOMER);
	           ticket.setPriority(TicketPriority.HIGH);
			   ticketManager.createTicket(ticket);
		    }   
		    resultModel.addObject("Ticket", ticket);
		}
		catch(TicketManagementException te){
			return resultModel.getAccessException();
		}
		return resultModel.getSuccess();
	}	
	
	/**
	   * This method is used to show the form to get a ticket detail
	   * 
	   * @return String
	   */
	@RequestMapping(value = "/customerTicketDetail", method = RequestMethod.GET)
	public String customerTicketDetail(@RequestParam("ticketId") int ticketId, Model model){
		Ticket ticket = null;
		try{
			ticket = ticketManager.getTicketById(ticketId);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
	return "ticket/customerTicketDetail";		
	}
	
	/**
	   * This method is used to show the form to show ticket detail
	   * 
	   * @return String
	   */
	@RequestMapping(value = "/customerTicketDetail/{ticketId}", method = RequestMethod.GET)
	public String getTicketDetail(@PathVariable("ticketId") int ticketId, Model model){
		Ticket ticket = null;
		Collection<TicketNote> notes = null;
		List<TicketNote> adminNotes = new ArrayList<TicketNote>();
		try{
			ticket = ticketManager.getTicketById(ticketId);
			/*if(ticket != null) {
			   notes = ticket.getNotes();
	     	   for(TicketNote note : notes){
	     		  if(TicketType.ADMIN.equals(note.getType()) || TicketType.AGENT.equals(note.getType()))
	     			 adminNotes.add(note);
	     	   }	 
			   ticket.setNotes(adminNotes);		    
		    }*/			
			User customer = ticket.getCustomer();
			userManager.setSessionUser(customer);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
 	    return "ticket/customerTicketDetail";		
	}
	
	 /**
	   * This method is used to process the ticket form input
	   * 
	   * @return ModelAndView
	   */
	@RequestMapping(value="/customerTicketDetail", method = RequestMethod.POST)
	public ModelAndView processCustomerTicketDetail(@ModelAttribute("ticket") Ticket ticket, BindingResult result){
		ResultModel resultModel = new ResultModel("ticket/customerTicketDetail");
		try {
			User customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
			ticket.setCustomer(customer);
			ticketManager.updateTicket(ticket);			
		    resultModel.addObject("ticket", ticket);
		}
		catch(TicketManagementException te){
			return resultModel.getAccessException();
		}
		return resultModel.getSuccess();
	}	
	
	/**
	  * This method is used to show update customer ticket form
	  * 
	  * @return String
	  */
	@RequestMapping(value="/updateCustomerTicket/{ticketId}", method = RequestMethod.GET)
	public String updateCustomerTicket(@PathVariable("ticketId") int ticketId, Model model){
        Ticket ticket = null;
        Collection<TicketNote> notes = null;
        Collection<TicketNote> customerNotes = new ArrayList<TicketNote>();
        int numOfNotes = 0;
		try{
			ticket = ticketManager.getTicketById(ticketId);
		}	    
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		if(ticket != null) {
		   notes = ticket.getNotes();
		   for(TicketNote note : notes){
			   //if(TicketType.CUSTOMER.equals(note.getType()))
				  customerNotes.add(note); 
		   }
		   numOfNotes = customerNotes.size();
	    }		
		model.addAttribute("ticket", ticket);
		model.addAttribute("numOfNotes", numOfNotes);
		return "ticket/updateCustomerTicket";	
	}
		
	/**
	  * This method is used to show update customer ticket form
	  * 
	  * @return ModelAndView
	  */
	@RequestMapping(value="/updateCustomerTicket/{ticketId}", method = RequestMethod.POST)
	public ModelAndView processUpdateCustomerTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result){     	
		ResultModel resultModelAndView = new ResultModel("ticket/customerCreateTicketSuccess");     	
     	try{    	   
	       ticketManager.customerUpdateTicket(ticket);
		   resultModelAndView.addObject("ticket", ticketManager.getTicketById(ticket.getId()));
	    }
	    catch(TicketManagementException te){
		    return resultModelAndView.getAccessException();
	    }
	    return resultModelAndView.getSuccess();			
	}
				
	/**
	 * Following are utility methods to load shared data 
	 * 
	 */
	@ModelAttribute("priorityList")
	public List<TicketPriority> getTicketPriority() {
	return Arrays.asList(TicketPriority.values());
	}
	
	@ModelAttribute("statusList")
	public List<TicketStatus> getTicketStatuses() {
	return Arrays.asList(TicketStatus.values());
	}
	
	@ModelAttribute("categoryList")
	public List<TicketCategory> getTicketCategory() {
	return Arrays.asList(TicketCategory.values());
	}
	
	@ModelAttribute("typeList")
	public List<TicketType> getTicketType() {
	return Arrays.asList(TicketType.values());
	}
	
	@ModelAttribute("userList")
	public List<User> getUsers(){
	  return userManager.getAllUsers();	
	}
	
	@ModelAttribute("adminUserList")
	public List<User> getAdminUserList(){
		return userManager.getAllAdmins();
	}
	
	@ModelAttribute("loggedinUser")
	public User getLoggedinUser(){
		return userManager.getLoggedInUser();
	}
	
}

