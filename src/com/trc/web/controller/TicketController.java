package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.trc.domain.support.ticket.Ticket;
import com.trc.domain.support.ticket.TicketCategory;
import com.trc.domain.support.ticket.TicketNote;
import com.trc.domain.support.ticket.TicketPriority;
import com.trc.domain.support.ticket.TicketStatus;
import com.trc.domain.support.ticket.system.impl.TicketManager;
import com.trc.exception.management.TicketManagementException;
import com.trc.manager.impl.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired 
	private TicketManager ticketManager;
	
	@Autowired 
	private UserManager userManager;
	
	/**
	   * This method is used to show all tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value={"", "/", "/ticketHome", "ticketOverview"}, method=RequestMethod.GET)
	public String showAllTickets(Model model){
		List<Ticket> ticketList = null;
		try{
		    ticketList = ticketManager.getAllTickets();
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("ticketList", ticketList);
		return "ticket/ticketOverview";
	}
	
	/**
	   * This method is used to show the form to show current logged in user's tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value="/showLoggedinUserTickets", method=RequestMethod.GET)
	public String showLoggedinUserTickets(Model model){
		List<Ticket> ticketList = null;
		User owner = userManager.getLoggedInUser();
		try{
		    ticketList = ticketManager.getTicketByOwner(owner.getUsername());
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("owner", owner);
		model.addAttribute("ticketList", ticketList);	
		return "ticket/ticketOverview";
	}
	
	/**
	   * This method is used to show all open tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value="/showOpenTickets", method=RequestMethod.GET)
	public String showOpenTickets(Model model){
		List<Ticket> ticketList = null;
		User owner = userManager.getLoggedInUser();
		try{
		    ticketList = ticketManager.getTicketByStatus(TicketStatus.OPEN);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("owner", owner);
		model.addAttribute("ticketList", ticketList);	
		return "ticket/ticketOverview";
	}
	
	/**
	   * This method is used to show the form to search tickets
	   * 
	   * @return ModelAndView
	   */
	@RequestMapping(value="/searchTickets", method=RequestMethod.GET)
	public String searchTickets(Model model){
		model.addAttribute("ticket", new Ticket());
		return "ticket/searchTickets";
	}
	
	/**
	   * This method is used to show the form to show the searched tickets
	   * 
	   * @return ModelAndView
	   */
	@RequestMapping(value="/searchTickets", method=RequestMethod.POST)
	public ModelAndView processSearchTickets(@RequestParam(value="ticketId", required=false) Integer ticketId, 
			                                 @RequestParam(value="keyword", required=false) String keyword,
			                                 @RequestParam(value="customerName", required=false) String customerName,
			                                 @RequestParam(value="ownerName", required=false) String ownerName){
		ResultModel resultModel = new ResultModel("ticket/showTickets");
		List<Ticket> ticketList = new ArrayList<Ticket>();
		if(ticketId != null) {
   	       try{
	    	   Ticket aTicket = ticketManager.getTicketById(ticketId);
	      	   ticketList.add(aTicket);
	      }
		  catch(TicketManagementException te){
			  return resultModel.getAccessException();
		  }	
		}
		if(keyword != null && !keyword.equals("")) {
	       try{
	    	   ticketList = ticketManager.getTicketByKeyword(keyword);
	    	   System.out.println("ticket list= "+ ticketList);
	       }
		   catch(TicketManagementException te){
			  return resultModel.getAccessException();
		   }	
		}
	   if(customerName != null && !customerName.equals("")) {
	   	   try{
		   	   ticketList = ticketManager.getTicketByCustomer(customerName);
		       resultModel.addObject("customerName", customerName);
		   }
		   catch(TicketManagementException te){
			  return resultModel.getAccessException();
		   }	
		}
	    if(ownerName != null && !ownerName.equals("")) {
	   	   try{
		   	   ticketList = ticketManager.getTicketByOwner(ownerName);
		   	   resultModel.addObject("ownerName", ownerName);
		   }
		   catch(TicketManagementException te){
			  return resultModel.getAccessException();
		   }	
		}
		resultModel.addObject("ticketList", ticketList);   
		return resultModel.getSuccess();   	   
	}
	
	 /**
	   * This method is used to show the form to create a ticket
	   * 
	   * @return ModelAndView
	   */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createTicket", method = RequestMethod.GET)
	public String createTicket(Model model){
		//List<TicketPriority> priorityList =  Arrays.asList(TicketPriority.values());
		//List<User> userList = userManager.getAllUsers();
		//List<User> adminUserList = userManager.getAllAdmins();
		//List adminUserList = userManager.getAllManagers();
		model.addAttribute("ticket", new Ticket());
		//resultModelAndView.addObject("adminUserList", adminUserList);
		//resultModelAndView.addObject("userList", userList);
		//resultModelAndView.addObject("priorityList", priorityList);
		return "ticket/createTicket";	
	}	
	
	 /**
	   * This method is used to process the ticket form to create a ticket
	   * 
	   * @return ModelAndView
	   */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createTicket", method = RequestMethod.POST)
	public ModelAndView processCreateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result){
		ResultModel resultModel = new ResultModel("ticket/createTicketSuccess");
		try {
			User customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
			ticket.setCustomer(customer);
			if(ticket.getOwner() == null){
			   ticket.setOwner(userManager.getLoggedInUser());
			   ticket.setCategory(TicketCategory.UNASSIGNED);
		    }
		    else {
		    	User owner = userManager.getUserByUsername(ticket.getOwner().getUsername());
		    	ticket.setOwner(owner);
		    	ticket.setCategory(TicketCategory.ASSIGNED);
		    }			
			ticket.setStatus(TicketStatus.OPEN);
			ticketManager.createTicket(ticket);
		    resultModel.addObject("Ticket", ticket);
		}
		catch(TicketManagementException te){
			return resultModel.getAccessException();
		}
		return resultModel.getSuccess();
	}	
	
	/**
	   * This method is used to show the form to update a ticket
	   * 
	   * @return String
	   */
	@RequestMapping(value = "/ticketDetail", method = RequestMethod.GET)
	public String ticketDetail(@RequestParam("ticketId") int ticketId, Model model){
		Ticket ticket = null;
		try{
			ticket = ticketManager.getTicketById(ticketId);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
    	return "ticket/ticketDetail";		
	}
	
	 /**
	   * This method is used to process the ticket form input to create a ticket
	   * 
	   * @return ModelAndView
	   */
	@RequestMapping(value="/ticketDetail", method = RequestMethod.POST)
	public ModelAndView processTicketDetail(@ModelAttribute("ticket") Ticket ticket, BindingResult result){
		ResultModel resultModel = new ResultModel("ticket/ticketDetail");
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
		
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/updateTicket", method = RequestMethod.GET)
	public String updateTicket(@RequestParam("ticketId") int ticketId, Model model){
        Ticket ticket = null;
        int numOfNotes = 0;
		try{
			ticket = ticketManager.getTicketById(ticketId);
		}	    
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		if(ticket != null) {
			Collection<TicketNote> notes = ticket.getNotes();
		    numOfNotes = notes.size();
	    }    
		
		model.addAttribute("ticket", ticket);
		model.addAttribute("numOfNotes", numOfNotes);
		return "ticket/updateTicket";	
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/updateTicket", method = RequestMethod.POST)
	public ModelAndView processUpdateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result){
     	ResultModel resultModelAndView = new ResultModel("ticket/ticketDetail");
     	User customer = null;
     	User owner = null;
     	Collection<TicketNote> ticketNotes = ticket.getNotes();
     	try{
	       if(ticket != null && ticket.getCustomer() != null) {	
		      customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
		      ticket.setCustomer(customer);
	       }   
	       if(ticket != null && ticket.getOwner() != null) {	
			  owner = userManager.getUserByUsername(ticket.getOwner().getUsername());
			  ticket.setOwner(owner);
		   }   
	       for(TicketNote ticketNote : ticketNotes) {	
	    	   if(ticketNote.getNote() != null && ticketNote.getNote().length() > 0) {
   			      ticketNote.setAuthor(userManager.getLoggedInUser());
			      ticketNote.setTicket(ticket);
	    	   }   
		   }  
	       ticketManager.updateTicket(ticket);
		   resultModelAndView.addObject("ticket", ticketManager.getTicketById(ticket.getId()));
	    }
	    catch(TicketManagementException te){
		    return resultModelAndView.getAccessException();
	    }
	    return resultModelAndView.getSuccess();	
	}
	
	/**
	   * This method is used to delete a ticket
	   * 
	   * @return ModelAndView
	   */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/deleteTicket", method = RequestMethod.GET)
	public String deleteTicket(@RequestParam("ticketId") int ticketId){
	    try{
	    	Ticket ticket = ticketManager.getTicketById(ticketId);	    	
			ticketManager.deleteTicket(ticket);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		return "redirect:";
	}
	
	@ModelAttribute("priorityList")
	public List<TicketPriority> getTicketPrioritys() {
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
	
	@ModelAttribute("userList")
	public List<User> getUsers(){
	  return userManager.getAllUsers();	
	}
	
	@ModelAttribute("adminUserList")
	public List<User> getAdminUserList(){
		return userManager.getAllAdmins();
	}
	
}

