package com.trc.web.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.trc.config.CONFIG;
import com.trc.manager.impl.UserManager;
import com.trc.security.encryption.StringEncrypter;
import com.trc.user.User;
import com.trc.web.session.SessionManager;
import com.trc.web.session.SessionRequest;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  @Autowired
  private UserManager userManager;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
      ServletException {
    User user = userManager.getLoggedInUser();
    userManager.getUserRealName(user);
    MDC.put("sessionId", SessionManager.getCurrentSession().getId());
    if (CONFIG.ADMIN && user.isInternalUser()) {
      MDC.put("internalUser", user.getUsername());
      setAlwaysUseDefaultTargetUrl(true);
      userManager.setSessionControllingUser(user);
      // userManager.setSessionUser(new AnonymousUser());
      if (user.isAdmin()) {
        setDefaultTargetUrl("/admin/home");
      } else if (user.isManager()) {
        setDefaultTargetUrl("/manager/home");
      } else if (user.isServiceRep()) {
        setDefaultTargetUrl("/servicerep/hom");
      } else if (user.isSuperUser()) {
        setDefaultTargetUrl("/it/home");
      }
    } else {
      MDC.put("username", user.getUsername());
      userManager.setSessionUser(user);
      setAlwaysUseDefaultTargetUrl(false);
      setDefaultTargetUrl("/manage");
    }

    SessionManager.set(SessionRequest.ENCRYPTER, new StringEncrypter(SessionManager.getCurrentSession().getId()));
    super.onAuthenticationSuccess(request, response, authentication);
  }

}