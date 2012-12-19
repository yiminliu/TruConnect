package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketCategory;
import com.trc.domain.ticket.TicketNote;
import com.trc.domain.ticket.TicketPriority;
import com.trc.domain.ticket.TicketStatus;
import com.trc.domain.ticket.TicketType;
import com.trc.exception.management.TicketManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.TicketManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/support/ticket/customer")
public class CustomerTicketController {
	@Autowired
	private TicketManager ticketManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;

	@Deprecated
	@RequestMapping(value = {"/all", "/overview"}, method = RequestMethod.GET)
	public ModelAndView showAllTickets() {
		ResultModel resultModel = new ResultModel("support/ticket/customer/overview");
		try {
			List<Ticket> ticketList = (List<Ticket>) ticketManager.getAllTickets();
			resultModel.addObject("ticketList", ticketList);
			return resultModel.getSuccess();
		} catch (TicketManagementException tme) {
			return resultModel.getAccessException();
		}
	}

	@Deprecated
	@RequestMapping(value = "/showLoggedinCustomerTickets", method = RequestMethod.GET)
	public ModelAndView showCustomerTickets() {
		ResultModel resultModel = new ResultModel("support/ticket/customer/showTickets");
		try {
			User user = userManager.getCurrentUser();
			List<Ticket> ticketList = ticketManager.getTicketByCustomer(user.getUsername());
			resultModel.addObject("customer", user);
			resultModel.addObject("ticketList", ticketList);
			/*
			 * for(Ticket ticket : ticketList){ notes = ticket.getNotes();
			 * for(TicketNote note : notes){
			 * if(TicketType.ADMIN.equals(note.getType()) ||
			 * TicketType.AGENT.equals(note.getType())) adminNotes.add(note); }
			 * ticket.setNotes(adminNotes); }
			 */
			return resultModel.getSuccess();
		} catch (TicketManagementException te) {
			return resultModel.getAccessException();
		}
	}

	/**
	 * This method is used to show the form to create a ticket
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerCreateTicket", method = RequestMethod.GET)
	public String customerCreateTicket(Model model) {
		model.addAttribute("ticket", new Ticket());
		return "support/ticket/customer/customerCreateTicket";
	}

	/**
	 * This method is used to process the ticket form to create a ticket
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerCreateTicket", method = RequestMethod.POST)
	public ModelAndView processCustomerCreateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		ResultModel resultModel = new ResultModel("support/ticket/customer/customerCreateTicketSuccess");
		try {
			if (ticket != null) {
				ticket.setType(TicketType.CUSTOMER);
				ticket.setPriority(TicketPriority.HIGH);
				ticket.setCustomer(userManager.getCurrentUser());
				ticket.setCreator(userManager.getCurrentUser());
				System.out.println("in controller, customer="+ticket.getCustomer());
				ticketManager.createTicket(ticket);
			}
			resultModel.addObject("Ticket", ticket);
		} catch (TicketManagementException te) {
			return resultModel.getAccessException();
		}
		return resultModel.getSuccess();
	}

	/**
	 * This method is used to show the form to get a ticket detail
	 * 
	 * @return String
	 */
	@Deprecated
	@RequestMapping(value = "/customerTicketDetail", method = RequestMethod.GET)
	public String customerTicketDetail(@RequestParam("ticketId") int ticketId, Model model) {
		Ticket ticket = null;
		try {
			ticket = ticketManager.getTicketById(ticketId);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
		return "support/ticket/customerTicketDetail";
	}

	/**
	 * This method is used to show the form to show ticket detail
	 * 
	 * @return String
	 */
	@Deprecated
	@RequestMapping(value = "/customerTicketDetail/{ticketId}", method = RequestMethod.GET)
	public String getTicketDetail(@PathVariable("ticketId") int ticketId, Model model) {
		Ticket ticket = null;
		Collection<TicketNote> notes = null;
		List<TicketNote> adminNotes = new ArrayList<TicketNote>();
		try {
			ticket = ticketManager.getTicketById(ticketId);
			/*
			 * if(ticket != null) { notes = ticket.getNotes(); for(TicketNote note :
			 * notes){ if(TicketType.ADMIN.equals(note.getType()) ||
			 * TicketType.AGENT.equals(note.getType())) adminNotes.add(note); }
			 * ticket.setNotes(adminNotes); }
			 */
			User customer = ticket.getCustomer();
			userManager.setSessionUser(customer);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
		return "support/ticket/customer/customerTicketDetail";
	}

	/**
	 * This method is used to process the ticket form input
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/customerTicketDetail", method = RequestMethod.POST)
	public ModelAndView processCustomerTicketDetail(@ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		ResultModel resultModel = new ResultModel("support/ticket/customer/customerTicketDetail");
		try {
			User customer = userManager.getUserByUsername(ticket.getCustomer().getUsername());
			ticket.setCustomer(customer);
			ticketManager.updateTicket(ticket);
			resultModel.addObject("ticket", ticket);
		} catch (TicketManagementException te) {
			return resultModel.getAccessException();
		}
		return resultModel.getSuccess();
	}

	/**
	 * This method is used to show update customer ticket form
	 * 
	 * @return String
	 */
	@Deprecated
	@RequestMapping(value = "/updateCustomerTicket/{ticketId}", method = RequestMethod.GET)
	public String updateCustomerTicket(@PathVariable("ticketId") int ticketId, Model model) {
		Ticket ticket = null;
		Collection<TicketNote> notes = null;
		Collection<TicketNote> customerNotes = new ArrayList<TicketNote>();
		int numOfNotes = 0;
		try {
			ticket = ticketManager.getTicketById(ticketId);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		if (ticket != null) {
			notes = ticket.getNotes();
			for (TicketNote note : notes) {
				// if(TicketType.CUSTOMER.equals(note.getType()))
				customerNotes.add(note);
			}
			numOfNotes = customerNotes.size();
		}
		model.addAttribute("ticket", ticket);
		model.addAttribute("numOfNotes", numOfNotes);
		return "support/ticket/customer/updateCustomerTicket";
	}

	/**
	 * This method is used to show update customer ticket form
	 * 
	 * @return ModelAndView
	 */
	@Deprecated
	@RequestMapping(value = "/updateCustomerTicket/{ticketId}", method = RequestMethod.POST)
	public ModelAndView processUpdateCustomerTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		ResultModel resultModelAndView = new ResultModel("support/ticket/customer/customerCreateTicketSuccess");
		try {
			ticketManager.customerUpdateTicket(ticket);
			resultModelAndView.addObject("ticket", ticketManager.getTicketById(ticket.getId()));
		} catch (TicketManagementException te) {
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
	public List<User> getUsers() {
		return userManager.getAllUsers();
	}

	@ModelAttribute("adminUserList")
	public List<User> getAdminUserList() {
		return userManager.getAllAdmins();
	}

	@ModelAttribute("loggedinUser")
	public User getLoggedinUser() {
		return userManager.getLoggedInUser();
	}

}
