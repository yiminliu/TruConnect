package com.trc.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.manager.UserManager;
import com.trc.security.encryption.Md5Encoder;
import com.trc.user.SecurityQuestionAnswer;
import com.trc.user.User;
import com.trc.user.authority.Authority;
import com.trc.web.model.ResultModel;
import com.trc.web.session.cache.CacheManager;
import com.trc.web.validation.AdminValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserManager userManager;
	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	private AdminValidator adminValidator;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/managers", method = RequestMethod.GET)
	public ModelAndView showServiceReps() {
		ResultModel model = new ResultModel("admin/managers");
		List<User> managers = userManager.getAllManagers();
		model.addObject("managers", managers);
		return model.getSuccess();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/managers/disable/{userId}", method = RequestMethod.GET)
	public String disableServiceRep(@PathVariable("userId") int userId) {
		User manager = userManager.getUserById(userId);
		userManager.disableUser(manager);
		return "redirect:/admin/managers";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/managers/enable/{userId}", method = RequestMethod.GET)
	public String enableServiceRep(@PathVariable("userId") int userId) {
		User manager = userManager.getUserById(userId);
		userManager.enableUser(manager);
		return "redirect:/admin/managers";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createServiceRep() {
		ResultModel model = new ResultModel("admin/create");
		model.addObject("user", new User());
		return model.getSuccess();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView postCreateServiceRep(HttpServletRequest request, @ModelAttribute User user, BindingResult result) {
		ResultModel model = new ResultModel("redirect:/admin/managers", "admin/create");

		SecurityQuestionAnswer userHint = new SecurityQuestionAnswer();
		userHint.setHintId(1);
		userHint.setHintAnswer("truconnect");
		user.setUsername(user.getEmail());
		user.setUserHint(userHint);
		user.setEnabled(true);
		user.setDateEnabled(new Date());
		user.getRoles().add(new Authority(user, request.getParameter("user_role")));

		adminValidator.validate(user, result);
		if (result.hasErrors()) {
			return model.getError();
		} else {
			user.setPassword(Md5Encoder.encode(user.getPassword()));
			userManager.saveUser(user);
			return model.getSuccess();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admins", method = RequestMethod.GET)
	public ModelAndView showAdmins() {
		ResultModel model = new ResultModel("admin/admins");
		List<User> admins = userManager.getAllAdmins();
		model.addObject("admins", admins);
		return model.getSuccess();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showAdminHome() {
		ResultModel model = new ResultModel("admin/home");
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

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/logout/{userId}", method = RequestMethod.GET)
	public String forceLogout(@PathVariable("userId") int userId) {
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

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	private void forceLogout(User user) {
		List<SessionInformation> sessionInfo = sessionRegistry.getAllSessions(user, false);
		for (SessionInformation si : sessionInfo) {
			si.expireNow();
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView showUser(HttpServletRequest request) {
		String userId = request.getParameter("admin_search_id");
		ResultModel model = new ResultModel("redirect:/account");
		setUserToView(userManager.getUserById(Integer.parseInt(userId)));
		return model.getSuccess();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@RequestMapping(value = "/search/email", method = RequestMethod.GET)
	public ModelAndView searchByEmail(HttpServletRequest request) {
		ResultModel model = new ResultModel("admin/search/jquery_username");
		List<User> searchResults = userManager.searchByEmail(request.getParameter("email"));
		model.addObject("searchResults", searchResults);
		return model.getSuccess();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	private void setUserToView(User user) {
		userManager.setSessionUser(user);
		CacheManager.clearCache();
	}
}