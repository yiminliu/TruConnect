package com.trc.web.validation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import com.trc.user.activation.Registration;

@Component
public class CaptchaValidator {

	public static void validate(HttpServletRequest request, Errors errors) {
		if (!SimpleImageCaptchaServlet.validateResponse(request, request.getParameter("captcha.value"))) {
			errors.rejectValue("captcha.value", "jcaptcha.incorrect", "Image Verificaiton failed");
		}
	}

	public static void validate(HttpServletRequest request, Registration reg, MessageContext messages) {
		if (!SimpleImageCaptchaServlet.validateResponse(request, request.getParameter("captcha.value"))) {
			MessageResolver message = new MessageBuilder().source("captcha.value").code("jcaptcha.incorrect").defaultText("Image Verification failed").build();
			messages.addMessage(message);
			reg.setJcaptcha(null);
		}
	}
}