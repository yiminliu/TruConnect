package com.trc.web.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.exception.management.AddressManagementException;
import com.trc.manager.impl.AddressManager;
import com.trc.manager.impl.UserManager;
import com.trc.user.User;
import com.trc.user.contact.Address;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.AddressValidator;

@Controller
@RequestMapping("/profile/address")
public class AddressController extends EncryptedController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AddressManager addressManager;
  @Autowired
  private AddressValidator addressValidator;

  private static Logger logger = LoggerFactory.getLogger(AddressController.class);

  @ModelAttribute
  private void addressReferenceData(ModelMap modelMap) {
    modelMap.addAttribute("states", Config.states.entrySet());
    modelMap.addAttribute("months", Config.months.entrySet());
    modelMap.addAttribute("years", Config.yearsFuture.entrySet());
    try {
      modelMap.addAttribute("addresses", addressManager.getAllAddresses(userManager.getCurrentUser()));
    } catch (AddressManagementException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public ModelAndView addAddress(HttpSession session) {
    ResultModel model = new ResultModel("profile/address/addAddress");
    // model.addObject("states", Config.states.entrySet());
    // model.addObject("months", Config.months.entrySet());
    // model.addObject("years", Config.yearsFuture.entrySet());
    model.addObject("address", new Address());
    return model.getSuccess();
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView postAddAddress(HttpSession session, @ModelAttribute Address address, BindingResult result) {
    ResultModel model = new ResultModel("redirect:/profile", "profile/address/addAddress");
    User user = userManager.getCurrentUser();
    addressValidator.validate(address, result);
    if (result.hasErrors()) {
      // model.addObject("states", Config.states.entrySet());
      // model.addObject("months", Config.months.entrySet());
      // model.addObject("years", Config.yearsFuture.entrySet());
      model.addObject("address", address);
      return model.getError();
    } else {
      try {
        addressManager.addAddress(user, address);
        return model.getSuccess();
      } catch (AddressManagementException e) {
        return model.getException();
      }
    }
  }

  @RequestMapping(value = "/edit/{encodedAddressId}", method = RequestMethod.GET)
  public ModelAndView editAddress(HttpSession session, @PathVariable("encodedAddressId") String encodedAddressId) {
    ResultModel model = new ResultModel("profile/address/editAddress");
    User user = userManager.getCurrentUser();
    try {
      Address address = addressManager.getAddress(user, super.decryptId(encodedAddressId));
      // model.addObject("states", Config.states.entrySet());
      // model.addObject("months", Config.months.entrySet());
      // model.addObject("years", Config.yearsFuture.entrySet());
      model.addObject("address", address);
      return model.getSuccess();
    } catch (AddressManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/edit/{encodedAddressId}", method = RequestMethod.POST)
  public ModelAndView postEditAddress(HttpSession session, @ModelAttribute Address address, BindingResult result) {
    ResultModel model = new ResultModel("redirect:/profile", "profile/address/editAddress");
    User user = userManager.getCurrentUser();
    // AddressValidator addressValidator = new AddressValidator();
    addressValidator.validate(address, result);
    if (result.hasErrors()) {
      // model.addObject("states", Config.states.entrySet());
      // model.addObject("months", Config.months.entrySet());
      // model.addObject("years", Config.yearsFuture.entrySet());
      model.addObject("address", address);
      return model.getError();
    } else {
      try {
        addressManager.updateAddress(user, address);
        return model.getSuccess();
      } catch (AddressManagementException e) {
        return model.getException();
      }
    }
  }

  @RequestMapping(value = "/remove/{encodedAddressId}", method = RequestMethod.GET)
  public ModelAndView removeAddress(HttpSession session, @PathVariable("encodedAddressId") String encodedAddressId) {
    ResultModel model = new ResultModel("profile/address/removeAddress");
    User user = userManager.getCurrentUser();
    try {
      Address address = addressManager.getAddress(user, super.decryptId(encodedAddressId));
      model.addObject("address", address);
      // model.addObject("states", Config.states.entrySet());
      // model.addObject("months", Config.months.entrySet());
      // model.addObject("years", Config.yearsFuture.entrySet());
      return model.getSuccess();
    } catch (AddressManagementException e) {
      return model.getException();
    }
  }

  @RequestMapping(value = "/remove/{encodedAddressId}", method = RequestMethod.POST)
  public ModelAndView postRemoveAddress(HttpSession session, @ModelAttribute Address address) {
    ResultModel model = new ResultModel("redirect:/profile");
    User user = userManager.getCurrentUser();
    try {
      addressManager.removeAddress(user, address);
      return model.getSuccess();
    } catch (AddressManagementException e) {
      return model.getException();
    }
  }
}
