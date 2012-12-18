package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.trc.manager.TicketManagerModel;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketManagerModel ticketManager;
	@Autowired
	private UserManager userManager;

	/**
	 * This method is used to show all tickets
	 * 
	 * @return String
	 */
	@RequestMapping(value = { "", "/", "/ticketHome", "/ticketOverview" }, method = RequestMethod.GET)
	public String ticketOverview(Model model) {
		List<Ticket> ticketList = null;
		try {
			ticketList = (List<Ticket>) ticketManager.getAllTickets();
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("ticketList", ticketList);
		return "ticket/ticketOverview";
	}

	/**
	 * This method is used to show the form to create a ticket
	 * 
	 * @return String
	 */
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/createTicket", method = RequestMethod.GET)
	public String createTicket(Model model) {
		model.addAttribute("ticket", new Ticket());
		User customer = userManager.getSessionUser();
		if (customer != null)
			return "ticket/createTicket";
		else
			return "ticket/preCustomerCreateTicket";
	}

	/**
	 * This method is used to show all tickets
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/showAllTickets", method = RequestMethod.GET)
	public String showAllTickets(Model model) {
		List<Ticket> ticketList = null;
		try {
			ticketList = (List<Ticket>) ticketManager.getAllTickets();
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("ticketList", ticketList);
		return "ticket/showTickets";
	}

	/**
	 * This method is used to process the ticket form to create a ticket
	 * 
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/createTicket", method = RequestMethod.POST)
	public ModelAndView processCreateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		ResultModel resultModel = new ResultModel("ticket/createTicketSuccess");
		if (ticket != null)
			ticket.setType(TicketType.AGENT);
		try {
			ticketManager.createTicket(ticket);
			resultModel.addObject("Ticket", ticket);
		} catch (TicketManagementException te) {
			return resultModel.getAccessException();
		}
		return resultModel.getSuccess();
	}

	/**
	 * This method is used to show the form to show current logged in user's
	 * tickets
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/showLoggedinUserTickets", method = RequestMethod.GET)
	public String showLoggedinUserTickets(Model model) {
		List<Ticket> ticketList = null;
		User assignee = userManager.getLoggedInUser();
		try {
			ticketList = (List<Ticket>) ticketManager.getTicketByAssignee(assignee.getUsername());
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("assignee", assignee);
		model.addAttribute("ticketList", ticketList);
		return "ticket/ticketOverview";
	}

	/**
	 * This method is used to show all open tickets
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/showOpenTickets", method = RequestMethod.GET)
	public String showOpenTickets(Model model) {
		List<Ticket> openTicketList = null;
		List<Ticket> reopenTicketList = null;
		List<Ticket> onholdTicketList = null;
		User Creator = userManager.getLoggedInUser();
		try {
			openTicketList = (List<Ticket>) ticketManager.getTicketByStatus(TicketStatus.OPEN);
			reopenTicketList = (List<Ticket>) ticketManager.getTicketByStatus(TicketStatus.REOPEN);
			onholdTicketList = (List<Ticket>) ticketManager.getTicketByStatus(TicketStatus.ON_HOLD);
			openTicketList.addAll(reopenTicketList);
			openTicketList.addAll(onholdTicketList);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("Creator", Creator);
		model.addAttribute("ticketList", openTicketList);
		return "ticket/showTickets";
	}

	/**
	 * This method is used to show the form to get a ticket detail
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/ticketDetail", method = RequestMethod.GET)
	public String ticketDetail(@RequestParam("ticketId") int ticketId, Model model) {
		Ticket ticket = null;
		try {
			ticket = ticketManager.getTicketById(ticketId);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
		return "ticket/ticketDetail";
	}

	/**
	 * This method is used to show the form to show ticket detail
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/ticketDetail/{ticketId}", method = RequestMethod.GET)
	public String getTicketDetail(@PathVariable("ticketId") int ticketId, Model model) {
		Ticket ticket = null;
		try {
			ticket = ticketManager.getTicketById(ticketId);
			User customer = ticket.getCustomer();
			userManager.setSessionUser(customer);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		model.addAttribute("ticket", ticket);
		return "ticket/ticketDetail";
	}

	/**
	 * This method is used to create a ticket
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ticketDetail", method = RequestMethod.POST)
	public ModelAndView processTicketDetail(@ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		ResultModel resultModel = new ResultModel("ticket/ticketDetail");
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
	 * This method is used to show the form to search tickets
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/searchTickets", method = RequestMethod.GET)
	public String searchTickets(Model model) {
		model.addAttribute("ticket", new Ticket());
		return "ticket/searchTickets";
	}

	/**
	 * This method is used to search tickets with given criteria
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchTickets", method = RequestMethod.POST)
	public ModelAndView processSearchTickets(@RequestParam(value = "ticketId", required = false) Integer ticketId,
			@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "creatorName", required = false) String creatorName) {
		ResultModel resultModel = new ResultModel("ticket/showTickets");
		List<Ticket> ticketList = new ArrayList<Ticket>();

		try {
			if (ticketId != null) {
				Ticket aTicket = ticketManager.getTicketById(ticketId);
				ticketList.add(aTicket);
			} else if (keyword != null && !keyword.equals("")) {
				ticketList = (List<Ticket>) ticketManager.getTicketByKeyword(keyword);
			} else if (customerName != null && !customerName.equals("")) {
				ticketList = (List<Ticket>) ticketManager.getTicketByCustomer(customerName);
				resultModel.addObject("customerName", customerName);
			} else if (creatorName != null && !creatorName.equals("")) {
				ticketList = (List<Ticket>) ticketManager.getTicketByCreator(creatorName);
				resultModel.addObject("creatorName", creatorName);
			}
		} catch (TicketManagementException te) {
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
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/updateTicket/{ticketId}", method = RequestMethod.GET)
	public String updateTicket(@PathVariable("ticketId") int ticketId, Model model) {
		Ticket ticket = null;
		int numOfNotes = 0;
		try {
			ticket = ticketManager.getTicketById(ticketId);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		if (ticket != null) {
			Collection<TicketNote> notes = ticket.getNotes();
			numOfNotes = notes.size();
		}
		model.addAttribute("ticket", ticket);
		model.addAttribute("numOfNotes", numOfNotes);
		return "ticket/updateTicket";
	}

	/**
	 * This method is used to update a ticket
	 * 
	 * @return String
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/updateTicket/{ticketId}", method = RequestMethod.POST)
	public ModelAndView processUpdateTicket(@ModelAttribute("ticket") Ticket ticket, BindingResult result) {
		ResultModel resultModelAndView = new ResultModel("ticket/ticketDetail");
		try {
			ticketManager.updateTicket(ticket);
			resultModelAndView.addObject("ticket", ticketManager.getTicketById(ticket.getId()));
		} catch (TicketManagementException te) {
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
	public String deleteTicket(@PathVariable("ticketId") int ticketId, Model model) {
		try {
			Ticket ticket = ticketManager.getTicketById(ticketId);
			ticketManager.deleteTicket(ticket);
			List<Ticket> ticketList = (List<Ticket>) ticketManager.getAllTickets();
			model.addAttribute("ticketList", ticketList);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		return "ticket/ticketOverview";
	}

	/**
	 * This method is used to delete a ticket
	 * 
	 * @return String
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String history(Model model) {
		try {
			User customer = userManager.getSessionUser();
			List<Ticket> ticketList = (List<Ticket>) ticketManager.getTicketByCustomer(customer.getUsername());
			model.addAttribute("customer", customer);
			model.addAttribute("ticketList", ticketList);
		} catch (TicketManagementException te) {
			throw new RuntimeException(te);
		}
		return "ticket/showUserTickets";
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

	@ModelAttribute("allTickets")
	public List<Ticket> getAllTickets() {
		try {
			return (List<Ticket>) ticketManager.getAllTickets();
		} catch (TicketManagementException tme) {
			return null;
		}
	}

	@ModelAttribute("userNameList")
	public List<String> getUserNames() {
		return userManager.getAllUserNames();
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
