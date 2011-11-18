package com.trc.web.validation;

import com.trc.web.validation.util.ValidationUtil;

public final class EmailValidator {
	public static final String emailRegExp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";

	public static final boolean checkEmail(String email) {
		if (!ValidationUtil.isEmpty(email) && email.toUpperCase().matches(emailRegExp)) {
			return true;
		} else {
			return false;
		}
	}
}