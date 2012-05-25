package com.trc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;

@Controller
@PreAuthorize("hasAnyRole('ROLE_SERVICEREP','ROLE_MANAGER','ROLE_ADMIN')")
public abstract class MemberController extends SearchController {
  @Autowired
  protected UserManager userManager;
  @Autowired
  protected SessionRegistry sessionRegistry;

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public abstract ModelAndView showHome();

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public abstract ModelAndView showMembers();

  protected ModelAndView showMembers(String title, List<User> users) {
    ResultModel model = new ResultModel("admin/members");
    if (Config.ADMIN) {
      model.addObject("members", users);
      model.addObject("memberType", title);
      return model.getSuccess();
    } else {
      return model.getAccessDenied();
    }
  }

}
