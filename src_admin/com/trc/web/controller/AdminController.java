package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.manager.impl.UserManager;
import com.trc.security.encryption.Md5Encoder;
import com.trc.user.SecurityQuestionAnswer;
import com.trc.user.User;
import com.trc.user.authority.Authority;
import com.trc.user.authority.ROLE;
import com.trc.util.cache.CacheManager;
import com.trc.util.logger.DevLogger;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.AdminValidator;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
@RequestMapping("/admin")
public class AdminController extends ManagerController {
  @Autowired
  private AdminValidator adminValidator;
  @Autowired
  private UserManager userManager;

  @RequestMapping(value = "cache/clear", method = RequestMethod.GET)
  public void clearCache(@PathVariable int userId) {
    User user = userManager.getCurrentUser();
    CacheManager.clearCache(user);
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public ModelAndView createServiceRep() {
    ResultModel model = new ResultModel("admin/create");
    if (!Config.ADMIN) {
      return model.getAccessDenied();
    }
    model.addObject("user", new User());
    return model.getSuccess();
  }

  @RequestMapping(value = "/logout/{userId}", method = RequestMethod.GET)
  public String forceLogout(@PathVariable("userId") int userId) {
    if (!Config.ADMIN) {
      return "exception/accessDenied";
    }
    List<Object> activePrincipals = sessionRegistry.getAllPrincipals();
    User activeUser;
    for (Object principal : activePrincipals) {
      activeUser = (User) principal;
      if (activeUser.getUserId() == userId) {
        forceLogout(activeUser);
        break;
      }
    }
    return "redirect:/admin";
  }

  private void forceLogout(User user) {
    List<SessionInformation> sessionInfo = sessionRegistry.getAllSessions(user, false);
    for (SessionInformation si : sessionInfo) {
      si.expireNow();
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ModelAndView postCreateServiceRep(HttpServletRequest request, @ModelAttribute User user, BindingResult result) {
    ResultModel model = new ResultModel("redirect:/admin/managers", "admin/create");
    if (!Config.ADMIN) {
      return model.getAccessDenied();
    }
    String requestedRole = request.getParameter("user_role");
    SecurityQuestionAnswer userHint = new SecurityQuestionAnswer();
    userHint.setHintId(1);
    userHint.setHintAnswer("truconnect");
    user.setUsername(user.getEmail());
    user.setUserHint(userHint);
    user.setEnabled(true);
    user.setDateEnabled(new Date());
    user.getRoles().add(new Authority(user, ROLE.valueOf(requestedRole)));
    adminValidator.validate(user, result);
    if (result.hasErrors()) {
      return model.getError();
    } else {
      user.setPassword(Md5Encoder.encode(user.getPassword()));
      userManager.saveUser(user);
      return model.getSuccess();
    }
  }

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
    DevLogger.log(userManager.getAllAdmins().toString());
    return showMembers("Administrator", userManager.getAllAdmins());
  }

}