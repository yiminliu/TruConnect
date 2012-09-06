package com.trc.web.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.trc.domain.support.ticket.TicketStorage;
import com.trc.exception.management.TicketManagementException;
import com.trc.manager.TicketManagerImpl;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.TicketValidator;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired 
	private TicketManagerImpl ticketManager;
	
	@Autowired 
	private UserManager userManager;
	
	@Autowired TicketValidator ticketValidator;
	
	/**
	   * This method is used to show all tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value={"", "/", "/ticketHome", "/ticketOverview"}, method=RequestMethod.GET)
	public String ticketLandingPage(Model model){
	   return "redirect:/ticket/1";
	}
	

	
	 /**
	   * This method is used to display the form to create a ticket
	   * 
	   * @return ModelAndView
	   */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/createTicket", method = RequestMethod.GET)
	public String createTicket(Model model){
		model.addAttribute("ticket", new Ticket());
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
		ticketValidator.validate(ticket, result);
		int ticketId = 0;
		if(result.hasErrors()){
		   resultModel = new ResultModel("ticket/createTicket");	
		   resultModel.addObject("ticket", ticket);
		   return resultModel.getError();
		}
		try {			
			ticketId = ticketManager.createTicket(ticket);		    
		}
		catch(TicketManagementException te){
			return resultModel.getAccessException();
		}
        try{
           ticketManager.sendEmailToAssignee(ticket.getAssignee().getEmail(), ticketId);	
        }
        catch(Exception e) {
        	e.printStackTrace(); // just swollow it
        }        
        resultModel.addObject("Ticket", ticket);
        return resultModel.getSuccess();
	}	
			
	/**
	   * This method is used to show all tickets and handle pagination
	   * 
	   * @return showAllTickets
	   */
	@RequestMapping(value={"/{page}"}, method=RequestMethod.GET)
	public String showAllTickets(Model model, @PathVariable("page") Integer page){
		List<Ticket> ticketList = null;		
		try{
		    ticketList = ticketManager.getAllTickets();
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		TicketStorage ticketStorage = new TicketStorage(ticketList);
		ticketStorage.setCurrentPageNum(page);
		model.addAttribute("ticketList", ticketList);
		model.addAttribute("ticketStorage", ticketStorage);
		return "ticket/ticketOverview";
	}
	
	
	/**
	   * This method is used to show all open and in_process tickets
	   * 
	   * @return String
	   */
	@RequestMapping(value="/showOpenTickets/{page}", method=RequestMethod.GET)
	public String showOpenTickets(Model model, @PathVariable("page") Integer page){
		List<Ticket> openTicketList = null;
		List<Ticket> inProcessTicketList = null;
		TicketStorage ticketStorage = null;
		try{
		    openTicketList = ticketManager.getTicketByStatus(TicketStatus.OPEN);
		    inProcessTicketList = ticketManager.getTicketByStatus(TicketStatus.IN_PROCESS);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		openTicketList.addAll(inProcessTicketList);
	    ticketStorage = new TicketStorage(openTicketList);
		ticketStorage.setCurrentPageNum(page);
		ticketStorage.setDisplayData("OPEN");
		model.addAttribute("ticketStorage", ticketStorage);
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
		model.addAttribute("userNameList", userManager.getAllUserNames());
		try{
			model.addAttribute("creators", ticketManager.getAllTicketCreators());
			model.addAttribute("ticketList", ticketManager.getAllTickets()); 
		}
		catch(TicketManagementException te) {
			te.printStackTrace();
		}
		return "ticket/searchTickets";
	}	

	
	/**
	   * This method is used to show the form to show the searched tickets
	   * 
	   * @return ModelAndView
	   */
	@RequestMapping(value="/searchTickets", method=RequestMethod.POST)
	public ModelAndView processSearchTickets(@ModelAttribute("ticket") Ticket ticket,  
			                                 @RequestParam(value="keyword", required=false) String keyword){
		ResultModel resultModel = new ResultModel("ticket/showTickets");
		List<Ticket> ticketList = new ArrayList<Ticket>();
		
		try {
		    if(ticket.getId() != null) {
 	       	   Ticket aTicket = ticketManager.getTicketById(ticket.getId());
	      	   ticketList.add(aTicket);
	        }
		    else if(keyword != null && !keyword.equals("")) {
	       	   ticketList = ticketManager.getTicketByKeyword(keyword);
	        }
		    else if(ticket.getCustomer() != null && !ticket.getCustomer().getUsername().equals("")) {
	   	   	   ticketList = ticketManager.getTicketByCustomer(ticket.getCustomer().getUsername());
		       resultModel.addObject("customerName", ticket.getCustomer().getUsername());
		   	}
		    else if(ticket.getCreator() != null && !ticket.getCreator().getUsername().equals("")) {
	   	   	   ticketList = ticketManager.getTicketByCreator(ticket.getCreator().getUsername());
		   	   resultModel.addObject("creatorName", ticket.getCreator().getUsername());
		    }
		    else if(ticket.getStatus() != null && !ticket.getStatus().equals("")) {
		   	   ticketList = ticketManager.getTicketByStatus(ticket.getStatus());
		    }
		}   
		catch(TicketManagementException te){
		   return resultModel.getAccessException();
		}						
		resultModel.addObject("ticketList", ticketList); 		
		return resultModel.getSuccess();   	   
	}
		
	/**
	   * This method is used to show the form to update a ticket
	   * 
	   * @return String
	   */
	@RequestMapping(value = "/ticketDetail", method = RequestMethod.GET)
	public String TicketDetail(@RequestParam("ticketId") int ticketId, Model model){
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
	   * This method is used to show the form to update a ticket
	   * 
	   * @return String
	   */
	@RequestMapping(value = "/ticketDetail/{ticketId}", method = RequestMethod.GET)
	public String getTicketDetail(@PathVariable("ticketId") int ticketId, Model model){
		Ticket ticket = null;
		try{
			ticket = ticketManager.getTicketById(ticketId);
			User customer = ticket.getCustomer();
			userManager.setSessionUser(customer);
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
	@RequestMapping(value="/updateTicket/{ticketId}", method = RequestMethod.GET)
	public String updateTicket(@PathVariable("ticketId") int ticketId, Model model){
        Ticket ticket = null;
    	try{
			ticket = ticketManager.getTicketById(ticketId);
		}	    
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		if(ticket != null) {
	       ticket.setNotes(new ArrayList<TicketNote>());
	    }		
		model.addAttribute("ticket", ticket);
		return "ticket/updateTicket";	
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value="/updateTicket/{ticketId}", method = RequestMethod.POST)
	public ModelAndView processUpdateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result){
     	ResultModel resultModelAndView = new ResultModel("ticket/ticketDetail");     	
     	try{	       
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
	   * @return String
	   */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/deleteTicket/{ticketId}", method = RequestMethod.GET)
	public String deleteTicket(@PathVariable("ticketId") int ticketId, Model model){
	    try{
	    	Ticket ticket = ticketManager.getTicketById(ticketId);	    	
			ticketManager.deleteTicket(ticket);
		 	model.addAttribute("ticketList", ticketManager.getAllTickets());
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		return "redirect:/ticket/1";
	}
	
	/**
	   * This method is used to delete a ticket
	   * 
	   * @return String
	   */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String history(Model model){
	    try{
	    	User customer = userManager.getCurrentUser();	    	
			
		    List<Ticket> ticketList = ticketManager.getTicketByCustomer(customer.getUsername());
		
		    model.addAttribute("customer", customer);
			model.addAttribute("ticketList", ticketList);
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		return "redirect:/ticket/showUserTickets";
	}
	
	/**
	   * This method is used to show the form to show current logged in user's tickets
	   * 
	   * @return String
	   */
	/*
	@RequestMapping(value="/showLoggedinUserTickets/{page}", method=RequestMethod.GET)
	public String showLoggedinUserTickets(Model model, @PathVariable("page") Integer page){
		List<Ticket> ticketList = null;
		User assignee = userManager.getLoggedInUser();
		try{
		    ticketList = ticketManager.getTicketByAssignee(assignee.getUsername());
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}
		model.addAttribute("assignee", assignee);
		model.addAttribute("ticketList", ticketList);	
		TicketStorage ticketStorage = new TicketStorage(ticketList);
		ticketStorage.setCurrentPageNum(page);
		model.addAttribute("ticketList", ticketList);
		model.addAttribute("ticketStorage", ticketStorage);
		return "ticket/ticketOverview";
	}
	*/	
	/**
	   * This method is used to show the form to show current logged in user's tickets
	   * 
	   * @return String
	   */
	/*@RequestMapping(value="/showLoggedinUserTickets/{status}/{page}", method=RequestMethod.GET)
	public String showLoggedinUserOpenTickets(Model model, @PathVariable("page") Integer page, @PathVariable("status") String status){
		List<Ticket> ticketList = null;
		List<Ticket> newTicketList = Collections.emptyList();
		User assignee = userManager.getLoggedInUser();
		try{
		    ticketList = ticketManager.getTicketByAssignee(assignee.getUsername());
		}
		catch(TicketManagementException te){
			throw new RuntimeException(te);
		}		
		for(Ticket ticket : ticketList) {
			if(ticket.getStatus() != null && ticket.getStatus().equals(status))
			   newTicketList.add(ticket);	
		}
		   	
		model.addAttribute("assignee", assignee);
		TicketStorage ticketStorage = new TicketStorage(newTicketList);
		ticketStorage.setCurrentPageNum(page);
		model.addAttribute("ticketList", newTicketList);
		model.addAttribute("ticketStorage", ticketStorage);
		return "ticket/ticketOverview";
	}
	*/
	
	
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

