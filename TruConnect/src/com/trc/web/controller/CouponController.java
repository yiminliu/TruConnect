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
		ResultModel model = new ResultModel("coupon/addCouponSuccess", "coupon/addCoupon");
		User user = userManager.getCurrentUser();
		String encodedAccountNumber = request.getParameter("account");
		int accountNumber = 0;
		if (encodedAccountNumber != null) {
			devLogger.log("Decrypting account number");
			accountNumber = super.decryptId(encodedAccountNumber);
			devLogger.log("Decrypted number is: " + accountNumber);
		}
		devLogger.log("Posting coupon request with account: " + accountNumber);
		try {
			coupon = couponManager.getCouponByCode(coupon.getCouponCode());
			couponValidator.validate(coupon, accountNumber, result);
			if (result.hasErrors()) {
				List<AccountDetail> accountList = accountManager.getOverview(user).getAccountDetails();
				encodeAccountNums(accountList);
				model.addObject("accountList", accountList);
				return model.getError();
			} else {
				devLogger.log("Found coupon " + coupon.getCouponId() + " with coupon code " + coupon.getCouponCode());
				devLogger.log("User " + user.getUsername() + " fetched");
				devLogger.log("Encoded account " + encodedAccountNumber);
				devLogger.log("Decoded account " + accountNumber);
				try {
					devLogger.log("Fetching account...");
					Account account = accountManager.getAccount(accountNumber);
					account = accountManager.getAccounts(user).get(0);
					devLogger.log("Fetchin service instance, there should only be one instance per account");
					ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);
					couponManager.applyCoupon(coupon, user, account, serviceInstance);
				} catch (AccountManagementException e) {
					devLogger.log("Something went wrong with account management: " + e.getMessage());
					model.getAccessException();
				}
				List<AccountDetail> accountList = accountManager.getOverview(user).getAccountDetails();
				AccountDetail accountDetail = null;
				for (AccountDetail ad : accountList) {
					if (ad.getAccount().getAccountno() == accountNumber) {
						accountDetail = ad;
					}
				}
				model.addObject("accountDetail", accountDetail);
				model.addObject("coupon", coupon);
				return model.getSuccess();
			}
		} catch (CouponManagementException e) {
			devLogger.log("Something went wrong with coupon Management: " + e.getMessage());
			return model.getAccessException();
		}
	}
}