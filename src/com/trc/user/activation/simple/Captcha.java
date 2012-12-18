package com.trc.user.activation.simple;

import java.io.Serializable;

public class Captcha implements Serializable {
	private static final long serialVersionUID = -6833126286061546983L;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
