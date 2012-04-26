package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.user.User;
import com.trc.user.admin.UserControl;
import com.trc.web.model.ResultModel;

@Controller
@PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN', 'ROLE_SUPERUSER')")
@RequestMapping("/manager")
public class ManagerController extends ServiceRepController {

  @Override
  public ModelAndView showHome() {
    ResultModel model = new ResultModel("admin/home");
    if (!Config.ADMIN) {
      return model.getAccessDenied();
    }
    List<Object> activePrincipals = sessionRegistry.getAllPrincipals();
    List<User> activeUsers = new ArrayList<User>();
    List<List<SessionInformation>> userSessionInfo = new ArrayList<List<SessionInformation>>();
    User activeUser;
    for (Object principal : activePrincipals) {
      activeUser = (User) principal;
      activeUsers.add(activeUser);
      userSessionInfo.add(sessionRegistry.getAllSessions(activeUser, false));
    }
    model.addObject("userSessionInfo", userSessionInfo);
    model.addObject("activeUsers", activeUsers);
    return model.getSuccess();
  }

  @Override
  public ModelAndView showMembers() {
    return showMembers("Manager", userManager.getAllManagers());
  }

  @RequestMapping(value = "/toggle/{userId}", method = RequestMethod.GET)
  public String toggleManager(@PathVariable("userId") int userId, @RequestParam("cmd") UserControl cmd) {
    if (Config.ADMIN) {
      User user = userManager.getUserById(userId);
      switch (cmd) {
      case ENABLE:
        userManager.enableUser(user);
        break;
      case DISABLE:
        userManager.disableUser(user);
        break;
      default:
        break;
      }
      return "redirect:/manager";
    } else {
      return "exception/accessDenied";
    }
  }

}
