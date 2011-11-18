package com.trc.web.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.trc.manager.UserManager;
import com.trc.security.encryption.StringEncrypter;
import com.trc.user.AnonymousUser;
import com.trc.user.User;
import com.trc.web.session.SessionKey;
import com.trc.web.session.SessionManager;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private UserManager userManager;

	@Autowired
	public void init(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User user = userManager.getLoggedInUser();
		userManager.getUserRealName(user);
		MDC.put("sessionId", SessionManager.getCurrentSessionId());
		if (user.isAdmin()) {
			MDC.put("internalUser", user.getUsername());
			userManager.setSessionAdmin(user);
			userManager.setSessionUser(new AnonymousUser());
			setAlwaysUseDefaultTargetUrl(true);
			setDefaultTargetUrl("/admin");
		} else if (user.isManager()) {
			MDC.put("internalUser", user.getUsername());
			userManager.setSessionManager(user);
			userManager.setSessionUser(new AnonymousUser());
			setAlwaysUseDefaultTargetUrl(true);
			setDefaultTargetUrl("/admin");
		} else {
			MDC.put("username", user.getUsername());
			userManager.setSessionUser(user);
			setAlwaysUseDefaultTargetUrl(false);
			setDefaultTargetUrl("/manage");
		}
		SessionManager.set(SessionKey.ENCRYPTER, new StringEncrypter(SessionManager.getCurrentSessionId()));
		super.onAuthenticationSuccess(request, response, authentication);
	}

}