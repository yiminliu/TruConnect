package com.trc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.config.Config;
import com.trc.manager.impl.UserManager;
import com.trc.user.User;

@Controller
@RequestMapping("/")
public class HomeController {
  private static final String contextPath = "";
  // private static final String contextPath = "TruConnect";
  private static final String redirect = "redirect:/";
  @Autowired
  private UserManager userManager;

  private String contextPath(String target) {
    return contextPath + "/" + target;
  }

  // private static ApplicationContext applicationContext;
  // private static String dynamicContextPath;
  //
  // @PostConstruct
  // public void setContextPath() {
  // dynamicContextPath = applicationContext.getDisplayName();
  // dynamicContextPath = contextPath.substring(contextPath.indexOf("'") + 1);
  // dynamicContextPath = contextPath.indexOf("-") == -1 ? contextPath :
  // contextPath.substring(0, contextPath.indexOf("-"));
  // }

  private String getUserHomepage() {
    User user = userManager.getLoggedInUser();
    if (Config.ADMIN) {
      if (user.isAdmin()) {
        return redirect("admin/home");
      } else if (user.isManager()) {
        return redirect("manager/home");
      } else if (user.isServiceRep()) {
        return redirect("servicerep/home");
      } else if (user.isSuperUser()) {
        return redirect("it/home");
      } else {
        return "admin_login";
      }
    } else {
      if (user.isUser()) {
        return redirect("manage");
      } else {
        return "home";
      }
    }
  }

  private String getLoginpage() {
    return Config.ADMIN ? "admin_login" : "login";
  }

  private String injectContextPath(HttpServletRequest request, String target) {
    // if (request.getContextPath().trim().isEmpty()) {
    // return redirect(contextPath(target));
    // } else {
    return target;
    // }
  }

  private String redirect(String target) {
    return redirect + target;
  }

  @RequestMapping(value = { "activation", "activate", "register" }, method = RequestMethod.GET)
  public String showActivation(HttpServletRequest request) {
    return redirect(injectContextPath(request, "registration"));
  }

  @RequestMapping(value = { "", "/", "home" }, method = RequestMethod.GET)
  public String showHome(HttpServletRequest request) {
    // if (request.getContextPath().trim().isEmpty()) {
    // return redirect(contextPath(""));
    // } else {
    return getUserHomepage();
    // }
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String showLogin(HttpServletRequest request) {
    User user = userManager.getLoggedInUser();
    if (user.isAuthenticated()) {
      return showHome(request);
    } else {
      // if (request.getContextPath().trim().isEmpty()) {
      // return redirect(contextPath(getLoginpage()));
      // } else {
      return getLoginpage();
      // }
    }
  }

  @RequestMapping(value = "/manage", method = RequestMethod.GET)
  public String showManagement(HttpServletRequest request) {
    return redirect(injectContextPath(request, "account"));
  }

  @RequestMapping(value = "/timeout", method = RequestMethod.GET)
  public String showTimeout() {
    return "exception/timeout";
  }

}