package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.AddressManagementException;
import com.trc.exception.management.PaymentManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.AddressManager;
import com.trc.manager.PaymentManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.account.PaymentHistory;
import com.trc.user.contact.Address;
import com.trc.web.model.ResultModel;
import com.trc.web.session.SessionKey;
import com.trc.web.session.SessionManager;
import com.trc.web.validation.CreditCardValidator;
import com.tscp.mvne.CreditCard;

@Controller
@RequestMapping("/account/payment")
public class PaymentController extends EncryptedController {
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private PaymentManager paymentManager;
	@Autowired
	private CreditCardValidator creditCardValidator;

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showPaymentHistory() {
		return "redirect:/account/payment/history/1";
	}

	@RequestMapping(value = "/history/{page}", method = RequestMethod.GET)
	public ModelAndView showPaymentHistory(@PathVariable("page") int page) {
		ResultModel model = new ResultModel("payment/history");
		User user = userManager.getCurrentUser();
		try {
			PaymentHistory paymentHistory = accountManager.getPaymentHistory(user);
			model.addObject("paymentHistory", paymentHistory);
			return model.getSuccess();
		} catch (AccountManagementException e) {
			return model.getAccessException();
		}
	}

	@RequestMapping(value = "/methods", method = RequestMethod.GET)
	public ModelAndView showPaymentMethods() {
		User user = userManager.getCurrentUser();
		ResultModel model = new ResultModel("payment/methods");
		try {
			List<CreditCard> paymentMethods = paymentManager.getCreditCards(user);
			List<String> encodedPaymentIds = new ArrayList<String>();
			for (CreditCard creditCard : paymentMethods) {
				encodedPaymentIds.add(super.encryptId(creditCard.getPaymentid()));
			}
			model.addObject("encodedPaymentIds", encodedPaymentIds);
			model.addObject("paymentMethods", paymentMethods);
			return model.getSuccess();
		} catch (PaymentManagementException e) {
			return model.getAccessException();
		}
	}

	@RequestMapping(value = "/methods/edit/{encodedPaymentId}", method = RequestMethod.GET)
	public ModelAndView editPaymentMethod(@PathVariable("encodedPaymentId") String encodedPaymentId) {
		ResultModel model = new ResultModel("payment/editCreditCard");
		try {
			int decodedPaymentId = super.decryptId(encodedPaymentId);
			CreditCard cardToUpdate = paymentManager.getCreditCard(decodedPaymentId);
			SessionManager.set(SessionKey.CREDITCARD_UPDATE, cardToUpdate);
			model.addObject("creditCard", cardToUpdate);
			return model.getSuccess();
		} catch (PaymentManagementException e) {
			return model.getAccessException();
		}
	}

	@RequestMapping(value = "/methods/edit/{encodedPaymentId}", method = RequestMethod.POST)
	public ModelAndView postEditPaymentMethod(@PathVariable("encodedPaymentId") String encodedPaymentId,
			@ModelAttribute CreditCard creditCard, BindingResult result) {
		ResultModel model = new ResultModel("redirect:/account/payment/methods", "payment/editCreditCard");
		creditCardValidator.validate(creditCard, result);
		if (result.hasErrors()) {
			return model.getError();
		} else {
			try {
				User user = userManager.getCurrentUser();
				if (creditCard.getCreditCardNumber().toLowerCase().contains("x")) {
					creditCard.setCreditCardNumber(null);
				}
				int decodedPaymentId = super.decryptId(encodedPaymentId);
				creditCard.setPaymentid(decodedPaymentId);
				paymentManager.updateCreditCard(user, creditCard);
				return model.getSuccess();
			} catch (PaymentManagementException e) {
				return model.getException();
			}
		}
	}

	@RequestMapping(value = "/methods/remove/{encodedPaymentId}", method = RequestMethod.GET)
	public ModelAndView deletePaymentMethod(@PathVariable("encodedPaymentId") String encodedPaymentId) {
		ResultModel model = new ResultModel("payment/deleteCreditCard");
		try {
			int decodedPaymentId = super.decryptId(encodedPaymentId);
			CreditCard creditCard = paymentManager.getCreditCard(decodedPaymentId);
			model.addObject("creditCard", creditCard);
			return model.getSuccess();
		} catch (PaymentManagementException e) {
			return model.getAccessException();
		}
	}

	@RequestMapping(value = "/methods/remove/{encodedPaymentId}", method = RequestMethod.POST)
	public ModelAndView postDeletePaymentMethod(@PathVariable("encodedPaymentId") String encodedPaymentId) {
		ResultModel model = new ResultModel("redirect:/account/payment/methods");
		User user = userManager.getCurrentUser();
		try {
			int decodedPaymentId = super.decryptId(encodedPaymentId);
			paymentManager.removeCreditCard(user, decodedPaymentId);
			return model.getSuccess();
		} catch (PaymentManagementException e) {
			return model.getException();
		}
	}

	@RequestMapping(value = "/methods/add", method = RequestMethod.GET)
	public ModelAndView addPaymentMethod() {
		User user = userManager.getCurrentUser();
		ResultModel model = new ResultModel("payment/addCreditCard_additional");
		try {
			CreditCard creditCard = new CreditCard();
			List<Address> addressList = addressManager.getAllAddresses(user);
			model.addObject("addresses", addressList);
			model.addObject("creditCard", creditCard);
			return model.getSuccess();
		} catch (AddressManagementException e) {
			return model.getAccessException();
		}
	}

	@RequestMapping(value = "/methods/add", method = RequestMethod.POST)
	public ModelAndView postAddPaymentMethod(@ModelAttribute CreditCard creditCard, BindingResult result) {
		ResultModel model = new ResultModel("redirect:/account/payment/methods", "payment/addCreditCard_additional");
		User user = userManager.getCurrentUser();
		creditCardValidator.validate(creditCard, result);
		if (result.hasErrors()) {
			try {
				List<Address> addressList = addressManager.getAllAddresses(user);
				model.addObject("addresses", addressList);
				return model.getError();
			} catch (AddressManagementException e) {
				return model.getAccessException();
			}
		} else {
			try {
				creditCard.setIsDefault(creditCard.getIsDefault() == null ? "N" : creditCard.getIsDefault());
				paymentManager.addCreditCard(user, creditCard);
				return model.getSuccess();
			} catch (PaymentManagementException e) {
				return model.getException();
			}
		}
	}

}
