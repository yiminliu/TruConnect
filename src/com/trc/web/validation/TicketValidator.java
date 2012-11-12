package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.trc.domain.ticket.Ticket;

@Component
public class TicketValidator implements Validator {

	@Override
	public boolean supports(Class<?> myClass) {
		return myClass.getClass().isAssignableFrom(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ticket ticket = (Ticket)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"required.ticket.title", "Title is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category",
				"required.ticket.category", "Category is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority",
				"required.ticket.priority", "Priority is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"required.ticket.description", "Description is required.");
	}

}
