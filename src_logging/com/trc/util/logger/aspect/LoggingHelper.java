package com.trc.util.logger.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.session.SessionManager;

@Component
public class LoggingHelper {
  @Autowired
  private UserManager userManager;

  private User getControllingUser() {
    User user = userManager.getSessionControllingUser() == null ? userManager.getLoggedInUser() : userManager.getSessionControllingUser();
    return user.isInternalUser() ? user : null;
  }

  private User getCurrentUser() {
    return userManager.getSessionUser() == null ? userManager.getLoggedInUser() : userManager.getSessionUser();
  }

  private String getUserStamp(User user) {
    StringBuilder userStamp = new StringBuilder();
    if (user != null) {
      if (user.isSuperUser()) {
        userStamp.append("IT:");
      } else if (user.isAdmin()) {
        userStamp.append("Admin:");
      } else if (user.isManager()) {
        userStamp.append("Manager:");
      } else if (user.isServiceRep()) {
        userStamp.append("ServiceRep:");
      } else if (!user.isEnabled()) {
        userStamp.append("Reserve:");
      }
      userStamp.append(user.getUserId());
      userStamp.append("(").append(user.getUsername()).append(") -");
    }
    return userStamp.toString();
  }

  public String getUserStamp() {
    StringBuilder userStamp = new StringBuilder();
    User currentUser = getCurrentUser();
    User controllingUser = getControllingUser();
    if (controllingUser != null) {
      userStamp.append(getUserStamp(controllingUser));
    }
    userStamp.append("[").append(SessionManager.getCurrentSession().getId()).append("] ");
    userStamp.append(getUserStamp(currentUser));
    return userStamp.toString();
  }
}
