package com.trc.user.activation.simple;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContextHolder;

import com.trc.exception.EmailException;
import com.trc.manager.UserManager;
import com.trc.security.encryption.Md5Encoder;
import com.trc.service.email.VelocityEmailService;
import com.trc.user.User;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;

@Component
public class SimpleRegistrationManager {
	@Autowired
	private UserManager userManager;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private VelocityEmailService velocityEmailService;

	public User createUser(SimpleRegistration registration) {
		User user = new User();
		user.setEmail(registration.getLogin().getEmail().getValue());
		user.setUsername(user.getEmail());
		user.setPassword(Md5Encoder.encode(registration.getLogin().getPassword().getValue()));
		user.setSecurityQuestionAnswer(registration.getSecurity().getSecurityQuestionAnswer());
		user.setDateEnabled(new Date());
		user.setEnabled(true);

		userManager.saveUser(user);
		userManager.setSessionUser(user);

		user.getContactInfo().setFirstName(user.getEmail());

		return user;
	}

	public void autoLogin(User user) {
		HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestContext().getExternalContext().getNativeRequest();
		// autoLogin(user, request, authenticationManager);
		userManager.autoLogin(user, authenticationManager);
	}

	protected void autoLogin(User user, HttpServletRequest request, AuthenticationManager authenticationManager) {
		userManager.autoLogin(user, request, authenticationManager);
	}

	public void sendAccountNotice(User user) {
		try {
			sendActivationEmail(user);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	@Loggable(value = LogLevel.TRACE)
	public void sendActivationEmail(User user) throws EmailException {
		try {
			if (user.getUserId() == 0) {
				user = userManager.getUserByUsername(user.getUsername());
			}
			SimpleMailMessage myMessage = new SimpleMailMessage();
			myMessage.setTo(user.getEmail());
			myMessage.setFrom("no-reply@webonthego.com");
			myMessage.setSubject("Your WebOnTheGo Account");
			Map<Object, Object> mailModel = new HashMap<Object, Object>();
			mailModel.put("user", user);
			mailModel.put("code", user.getPassword().substring(0, 8));
			mailModel.put("password", user.getPassword());
			velocityEmailService.send("welcome", myMessage, mailModel);
		} catch (EmailException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
