package com.trc.web.controller;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.CONFIG;
import com.trc.manager.impl.UserManager;
import com.trc.user.User;
import com.trc.web.model.ResultModel;
import com.trc.web.session.cache.CacheManager;

@Controller
@PreAuthorize("hasAnyRole('ROLE_SERVICEREP','ROLE_MANAGER','ROLE_ADMIN', 'ROLE_SUPERUSER')")
@RequestMapping("/search")
public class SearchController {
  @Autowired
  protected UserManager userManager;

  /**
   * Searches by User ID and email.
   * 
   * @param param
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  protected ModelAndView search(@RequestParam String param) {
    ResultModel model = new ResultModel("admin/search/jquery_username");
    if (!CONFIG.ADMIN) {
      return model.getAccessDenied();
    }
    List<User> searchResults = new Vector<User>();
    try {
      int numericSearch = Integer.parseInt(param);
      searchResults.add(userManager.getUserById(numericSearch));
      searchResults.add(userManager.searchByAccountNo(numericSearch));
    } catch (NumberFormatException e) {
      searchResults.addAll(userManager.searchByEmail(param));
    }
    model.addObject("searchResults", searchResults);
    return model.getSuccess();
  }

  @RequestMapping(method = RequestMethod.POST)
  protected ModelAndView showUser(@RequestParam String admin_search_id) {
    ResultModel model = new ResultModel("redirect:/account");
    if (!CONFIG.ADMIN) {
      return model.getAccessDenied();
    }
    setUserToView(userManager.getUserById(Integer.parseInt(admin_search_id)));
    return model.getSuccess();
  }

  protected void setUserToView(User user) throws AccessDeniedException {
    if (CONFIG.ADMIN) {
      userManager.setSessionUser(user);
      CacheManager.clearCache();
    } else {
      throw new AccessDeniedException("Application is not configured as Administrative");
    }
  }

  /**
   * Uses getJson to perform ajax searches. This is much more efficient but
   * currently unused.
   * 
   * @param email
   * @return
   */
  @RequestMapping(value = "getjson/email/ajax", method = RequestMethod.GET)
  public @ResponseBody
  SearchResponse searchByEmailAjax(@RequestParam String email) {
    if (!CONFIG.ADMIN) {
      return new SearchResponse(false, new String[0]);
    }
    List<User> searchResults = userManager.searchByEmail(email);
    String[] emails = new String[searchResults.size()];
    for (int i = 0; i < searchResults.size(); i++) {
      emails[i] = searchResults.get(i).getEmail();
    }
    if (searchResults.size() > 0) {
      return new SearchResponse(true, emails);
    } else {
      return new SearchResponse(false, emails);
    }
  }

  public static class SearchResponse {
    private String[] users;
    private boolean success;

    public String[] getUsers() {
      return users;
    }

    public boolean isSuccess() {
      return success;
    }

    public SearchResponse(boolean success, String[] users) {
      this.success = success;
      this.users = users;
    }
  }
}
