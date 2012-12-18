package com.trc.user.activation.simple;

import java.io.Serializable;

import com.trc.user.SecurityQuestionAnswer;

public class SimpleRegistrationSecurity implements Serializable {
	private static final long serialVersionUID = 4860793350933925819L;
	private SecurityQuestionAnswer securityQuestionAnswer = new SecurityQuestionAnswer();
	private Captcha captcha = new Captcha();

	public SecurityQuestionAnswer getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}

	public void setSecurityQuestionAnswer(SecurityQuestionAnswer securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}

	public Captcha getCaptcha() {
		return captcha;
	}

	public void setCaptcha(Captcha captcha) {
		this.captcha = captcha;
	}

}
