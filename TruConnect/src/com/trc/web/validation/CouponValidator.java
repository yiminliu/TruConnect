package com.trc.web.validation;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.coupon.Coupon;
import com.trc.coupon.UserCoupon;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.CouponManager;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;

@Component
public class CouponValidator implements Validator {
	@Autowired
	private CouponManager couponManager;
	@Resource
	private DevLogger devLogger;

	@Override
	public boolean supports(Class<?> myClass) {
		return Coupon.class.isAssignableFrom(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Coupon coupon = (Coupon) target;
		checkExists(coupon, errors);
		if (coupon != null) {
			checkCouponCode(coupon.getCouponCode(), errors);
			checkStartDate(coupon.getStartDate(), errors);
			checkEndDate(coupon.getEndDate(), errors);
			checkAvailability(coupon, errors);
		}
	}

	private void checkCouponCode(String couponCode, Errors errors) {
		if (couponCode == null || couponCode.length() < 3 || !couponCode.substring(0, 3).equals("tru")) {
			errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
		}
	}

	private void checkEndDate(Date endDate, Errors errors) {
		if (endDate != null && endDate.compareTo(new Date()) > 0) {
			errors.rejectValue("endDate", "coupon.date.expired", "Coupon is expired");
		}
	}

	private void checkStartDate(Date startDate, Errors errors) {
		if (startDate != null && startDate.compareTo(new Date()) < 0) {
			errors.rejectValue("startDate", "coupon.date.notActive", "Coupon has not started");
		}
	}

	private void checkAvailability(Coupon coupon, Errors errors) {
		if (coupon.getQuantity() > 0 && (coupon.getQuantity() - coupon.getUsed()) < 1) {
			errors.rejectValue("quantity", "coupon.quantity.insufficient", "This coupon has been exhausted");
		}
	}

	private void checkExists(Coupon coupon, Errors errors) {
		if (coupon == null) {
			errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
		}
	}

	public boolean checkUsed(Coupon coupon, User user, Account account) {
		devLogger.log("Checking if coupon has already been redeemed...");
		try {
			List<UserCoupon> userCoupons = couponManager.getUserCoupon(coupon, user, account);
			if (userCoupons.size() < 1) {
				devLogger.log("Coupon has not yet been applied");
				return false;
			} else {
				devLogger.log("Coupon has already been applied. Found " + userCoupons.size() + " for user " + user.getUserId()
						+ " on account " + account.getAccountno());
				return true;
			}
		} catch (CouponManagementException e) {
			return true;
		}
	}

}
