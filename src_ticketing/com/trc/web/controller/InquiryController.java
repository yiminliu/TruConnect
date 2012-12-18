package com.trc.web.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.domain.ticket.Ticket;
import com.trc.domain.ticket.TicketCategory;
import com.trc.domain.ticket.TicketPriority;
import com.trc.domain.ticket.TicketType;
import com.trc.exception.management.TicketManagementException;
import com.trc.manager.TicketManagerModel;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/support/inquire")
public class InquiryController {
	@Autowired
	private TicketManagerModel ticketManager;

	@RequestMapping(method = RequestMethod.GET)
	public String showInqiuryPage() {
		return "support/inquire/overview";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView showInquiryForm() {
		ResultModel resultModel = new ResultModel("support/inquire/createTicket");
		resultModel.addObject("ticket", new Ticket());
		resultModel.addObject("categoryList", Arrays.asList(TicketCategory.values()));
		return resultModel.getSuccess();
	}

	// TODO create validator for tickets
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView processCustomerCreateTicket(@ModelAttribute("ticket") Ticket ticket) {
		ResultModel resultModel = new ResultModel("support/inquire/createTicketSuccess");
		try {
			if (ticket != null) {
				ticket.setType(TicketType.CUSTOMER);
				ticket.setPriority(TicketPriority.HIGH);
				ticketManager.createTicket(ticket);
				resultModel.addObject("Ticket", ticket);
				return resultModel.getSuccess();
			} else {
				return resultModel.getException();
			}
		} catch (TicketManagementException te) {
			return resultModel.getAccessException();
		}

	}

}
