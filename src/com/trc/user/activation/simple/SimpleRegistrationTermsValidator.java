package com.trc.user.activation.simple;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SimpleRegistrationTermsValidator implements Validator {

	@Override
	public boolean supports(Class<?> myClass) {
		return SimpleRegistrationTerms.class.isAssignableFrom(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SimpleRegistrationTerms terms = (SimpleRegistrationTerms) target;
		if (!terms.isAcceptTerms()) {
			errors.rejectValue("acceptTerms", "terms.required", "You must accept the terms and conditions");
		}
	}

}
