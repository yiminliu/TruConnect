package com.trc.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.config.Config;
import com.trc.exception.management.DeviceManagementException;
import com.trc.manager.DeviceManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.tscp.mvne.Device;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private UserManager userManager;
	@Autowired
	private DeviceManager deviceManager;

	private String getHomePage() {
		User user = userManager.getLoggedInUser();
		if (Config.ADMIN) {
			if (user.isUser())
				return getUserHomePage(user);
			else
				return getAdminHomepage(user);
		} else {
			return getUserHomePage(user);
		}
	}

	private String getUserHomePage(User user) {
		boolean isUser = user.isUser();
		boolean hasDevice;
		try {
			List<Device> devices = deviceManager.getDeviceInfoList(user);
			hasDevice = devices != null && !devices.isEmpty();
		} catch (DeviceManagementException e) {
			return "home";
		}
		return isUser && hasDevice ? "redirect:/account" : "start";
	}

	private String getAdminHomepage(User user) {
		if (user.isAdmin()) {
			return "admin/home";
		} else if (user.isManager()) {
			return "manager/home";
		} else if (user.isServiceRep()) {
			return "servicerep/home";
		} else if (user.isSuperUser()) {
			return "it/home";
		} else {
			return "admin/login";
		}
	}

	private String getLoginpage() {
		return Config.ADMIN ? "admin/login" : "login";
	}

	@RequestMapping(value = { "", "/", "home" }, method = RequestMethod.GET)
	public String showHome(HttpServletRequest request) {
		return getHomePage();
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String showLogin(HttpServletRequest request) {
		User user = userManager.getLoggedInUser();
		if (user.isAuthenticated()) {
			return "redirect:/" + showHome(request);
		} else {
			return getLoginpage();
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		return "redirect:/j_spring_security_logout";
	}

	@RequestMapping(value = "timeout", method = RequestMethod.GET)
	public String showTimeout() {
		return "exception/timeout";
	}

}