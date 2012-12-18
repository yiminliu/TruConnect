package com.trc.user.activation.simple;

import java.io.Serializable;

public class SimpleRegistrationTerms implements Serializable {
	private static final long serialVersionUID = 8240939366018677082L;
	private boolean acceptTerms;

	public boolean isAcceptTerms() {
		return acceptTerms;
	}

	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

}
