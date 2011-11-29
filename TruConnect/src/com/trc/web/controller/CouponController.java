package com.trc.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.coupon.Coupon;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.CouponManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.util.logger.DevLogger;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Controller
@RequestMapping("/coupons")
public class CouponController extends EncryptedController {
	@Autowired
	private CouponManager couponManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private CouponValidator couponValidator;
	@Resource
	private DevLogger devLogger;

	private void encodeAccountNums(List<AccountDetail> accountDetailList) {
		for (AccountDetail accountDetail : accountDetailList) {
			accountDetail.setEncodedAccountNum(super.encryptId(accountDetail.getAccount().getAccountno()));
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showRedeemCoupon() {
		ResultModel model = new ResultModel("coupon/addCoupon");
		User user = userManager.getCurrentUser();
		Overview overview = accountManager.getOverview(user);
		encodeAccountNums(overview.getAccountDetails());
		List<AccountDetail> accountList = overview.getAccountDetails();
		model.addObject("coupon", new Coupon());
		model.addObject("accountList", accountList);
		return model.getSuccess();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView postRedeemCoupon(HttpServletRequest request, @ModelAttribute("coupon") Coupon coupon,
			BindingResult result) {
		devLogger.log("Form submitted to redeem coupon...");
		// TODO create a succes page
		ResultModel model = new ResultModel("coupon/addCouponSuccess", "coupon/addCoupon");
		coupon = couponManager.getCouponByCode(coupon.getCouponCode());

		couponValidator.validate(coupon, result);
		if (result.hasErrors()) {
			return model.getError();
		} else {
			devLogger.log("Found coupon " + coupon.getCouponId() + " with coupon code " + coupon.getCouponCode());
			User user = userManager.getCurrentUser();
			devLogger.log("User " + user.getUsername() + " fetched");
			String encodedAccountNumber = request.getParameter("account");
			devLogger.log("Encoded account " + encodedAccountNumber);
			int accountNumber = super.decryptId(encodedAccountNumber);
			devLogger.log("Decoded account " + accountNumber);
			try {
				devLogger.log("Fetching account...");
				Account account = accountManager.getAccount(accountNumber);
				account = accountManager.getAccounts(user).get(0);
				devLogger.log("Fetchin service instance, there should only be one instance per account");
				ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);
				if (couponManager.applyCoupon(coupon, user, account, serviceInstance)) {
					return model.getSuccess();
				} else {
					return model.getAccessException();
				}
			} catch (AccountManagementException e) {
				devLogger.log("Something went wrong with account management");
				e.printStackTrace();
			} catch (CouponManagementException e) {
				devLogger.log("Something went wrong with coupon management");
				e.printStackTrace();
			}
			return model.getSuccess();
		}
	}
}
