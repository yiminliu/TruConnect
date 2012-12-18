package com.trc.user.activation.simple;

import java.io.Serializable;

public class SimpleRegistration implements Serializable {
	private static final long serialVersionUID = -204153305944601962L;
	private SimpleRegistrationLogin login = new SimpleRegistrationLogin();
	private SimpleRegistrationSecurity security = new SimpleRegistrationSecurity();
	private SimpleRegistrationTerms terms = new SimpleRegistrationTerms();

	public SimpleRegistrationLogin getLogin() {
		return login;
	}

	public void setLogin(SimpleRegistrationLogin login) {
		this.login = login;
	}

	public SimpleRegistrationSecurity getSecurity() {
		return security;
	}

	public void setSecurity(SimpleRegistrationSecurity security) {
		this.security = security;
	}

	public SimpleRegistrationTerms getTerms() {
		return terms;
	}

	public void setTerms(SimpleRegistrationTerms terms) {
		this.terms = terms;
	}

}
