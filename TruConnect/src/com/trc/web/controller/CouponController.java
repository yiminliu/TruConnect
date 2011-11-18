package com.trc.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.trc.web.model.ResultModel;
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
	public ModelAndView postRedeemCoupon(HttpServletRequest request, @ModelAttribute("coupon") Coupon coupon) {
		ResultModel model = new ResultModel("test/success");
		coupon = couponManager.getCouponByCode(coupon.getCouponCode());
		User user = userManager.getCurrentUser();
		String encodedAccountNumber = request.getParameter("account");
		int accountNumber = super.decryptId(encodedAccountNumber);

		try {
			Account account = accountManager.getAccount(accountNumber);
			account = accountManager.getAccounts(user).get(0);
			ServiceInstance serviceInstance = account.getServiceinstancelist().get(0);
			couponManager.redeemCoupon(coupon, user, account, serviceInstance);
		} catch (AccountManagementException e) {
			System.out.println("TC! something wrong with account retrieval");
			e.printStackTrace();
		} catch (CouponManagementException e) {
			System.out.println("TC! something wrong with coupon redemption");
			e.printStackTrace();
		}

		return model.getSuccess();
	}
}
