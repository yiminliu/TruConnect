package com.trc.user.activation.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.manager.UserManager;
import com.trc.web.validation.EmailValidator;
import com.trc.web.validation.ValidationUtil;

@Component
public class SimpleRegistrationLoginValidator implements Validator {
	@Autowired
	private UserManager userManager;
	public static final int MAX_PASS_SIZE = 100;
	public static final int MIN_PASS_SIZE = 5;
	public static final int MAX_NAME_SIZE = 100;
	public static final int MIN_NAME_SIZE = 5;

	@Override
	public boolean supports(Class<?> myClass) {
		return SimpleRegistrationLogin.class.isAssignableFrom(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SimpleRegistrationLogin registration = (SimpleRegistrationLogin) target;
		checkEmail(registration.getEmail(), errors);
		checkPassword(registration.getPassword(), errors);
	}

	protected void checkEmail(ConfirmableFormField email, Errors errors) {
		errors.pushNestedPath("email");
		if (ValidationUtil.isEmpty(email.value)) {
			errors.rejectValue("value", "email.required", "You must enter an e-mail address");
		} else if (!EmailValidator.checkEmail(email.value)) {
			errors.rejectValue("value", "email.invalid", "E-mail address is invalid");
		} else if (!ValidationUtil.matches(email.value, email.confirmValue)) {
			errors.rejectValue("confirmValue", "email.mismatch", "Your e-mails do not match");
		} else if (!userManager.isEmailAvailable(email.value)) {
			String[] args = { email.value };
			errors.rejectValue("value", "email.unavailable", args, "E-mail address is unavailable");
		}
		errors.popNestedPath();
	}

	protected static void checkPassword(ConfirmableFormField password, Errors errors) {
		errors.pushNestedPath("password");
		if (ValidationUtil.isEmpty(password.value)) {
			errors.rejectValue("value", "password.required", "You must enter a password");
		} else if (!ValidationUtil.isBetween(password.value, MIN_PASS_SIZE, MAX_PASS_SIZE)) {
			errors.rejectValue("value", "password.size", "Your password must be " + MIN_PASS_SIZE + " to " + MAX_PASS_SIZE + " characters");
		} else if (!ValidationUtil.isAlphaNumeric(password.value)) {
			errors.rejectValue("value", "password.invalid", "Your password must be alphanumeric");
		} else if (!ValidationUtil.matches(password.value, password.confirmValue)) {
			errors.rejectValue("confirmValue", "password.mismatch", "Your passwords do not match");
		}
		errors.popNestedPath();
	}

}
