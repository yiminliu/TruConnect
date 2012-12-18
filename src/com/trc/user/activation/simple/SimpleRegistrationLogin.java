package com.trc.user.activation.simple;

import java.io.Serializable;

public class SimpleRegistrationLogin implements Serializable {
	private static final long serialVersionUID = -4211564087975547505L;
	private ConfirmableFormField email = new ConfirmableFormField();
	private ConfirmableFormField password = new ConfirmableFormField();

	public ConfirmableFormField getEmail() {
		return email;
	}

	public void setEmail(ConfirmableFormField email) {
		this.email = email;
	}

	public ConfirmableFormField getPassword() {
		return password;
	}

	public void setPassword(ConfirmableFormField password) {
		this.password = password;
	}

}