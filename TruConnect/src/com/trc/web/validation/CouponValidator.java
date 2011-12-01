package com.trc.web.validation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponStackable;
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
			checkQuantity(coupon, errors);
		}
	}

	public void validate(Coupon coupon, String accountNumber, Errors errors) {
		validate(coupon, errors);
		if (accountNumber == null || accountNumber.isEmpty()) {
			errors.reject("You must choose a device to apply the coupon to");
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

	private void checkQuantity(Coupon coupon, Errors errors) {
		if (coupon.getQuantity() > 0 && (coupon.getQuantity() - coupon.getUsed()) < 1) {
			errors.rejectValue("quantity", "coupon.quantity.insufficient", "This coupon has been exhausted");
		}
	}

	private void checkExists(Coupon coupon, Errors errors) {
		if (coupon == null) {
			errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
		}
	}

	public boolean isEligible(Coupon coupon, User user, Account account) {
		boolean isAvailable = isAvailable(coupon);
		boolean isApplied = isApplied(coupon, user, account);
		boolean isAtAccountLimit = isAtAccountLimit(coupon, user, account);
		boolean isExistingStackable;
		boolean isStackable;
		try {
			List<UserCoupon> userCoupons = couponManager.getUserCoupons(user.getUserId());
			Coupon existingCoupon;
			for (UserCoupon uc : userCoupons) {
				existingCoupon = uc.getId().getCoupon();
				isExistingStackable = isStackable(existingCoupon);
				if (isExistingStackable) {
					isStackable = isStackable(existingCoupon, coupon);
				} else {
					isStackable = isStackable(coupon, existingCoupon);
				}
				if (!isStackable) {
					return false;
				}
			}
			boolean result = isAvailable && !isApplied && !isAtAccountLimit;
			return result;
		} catch (CouponManagementException e) {
			return false;
		}
	}

	public boolean isStackable(Coupon coupon) {
		Collection<CouponStackable> stackable = coupon.getCouponDetail().getStackable();
		return stackable.size() > 0;
	}

	public boolean isStackable(Coupon stackableCoupon, Coupon candidate) {
		Collection<CouponStackable> stackable = stackableCoupon.getCouponDetail().getStackable();
		for (CouponStackable cs : stackable) {
			if (cs.getId().getStackableCouponDetailId() == candidate.getCouponDetail().getCouponDetailId()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAvailable(Coupon coupon) {
		return (coupon.getQuantity() - coupon.getUsed()) > 0;
	}

	public boolean isApplied(Coupon coupon, User user, Account account) {
		try {
			UserCoupon userCoupon = couponManager.getUserCoupon(new UserCoupon(coupon, user, account));
			return userCoupon != null;
		} catch (CouponManagementException e) {
			devLogger.log("Error checking if coupon has already been applied while fetching UserCoupon");
			return true;
		}
	}

	public boolean isAtAccountLimit(Coupon coupon, User user, Account account) {
		int accountLimit = coupon.getCouponDetail().getAccountLimit() == null ? -1 : coupon.getCouponDetail()
				.getAccountLimit();
		if (accountLimit == -1) {
			return false;
		}
		// devLogger.log("account limit on coupon " + coupon.getCouponId() + " is "
		// + accountLimit);
		int count = 0;
		try {
			List<UserCoupon> userCoupons = couponManager.getUserCoupons(user.getUserId());
			for (UserCoupon userCoupon : userCoupons) {
				if (userCoupon.getId().getCoupon().getCouponDetail().getCouponDetailId() == coupon.getCouponDetail()
						.getCouponDetailId()) {
					count++;
				}
			}
			// devLogger.log("found " + count +
			// " instances of matching coupon details in the user's existing coupons");
			return count >= accountLimit;
		} catch (CouponManagementException e) {
			return true;
		}
	}

	public boolean isRecurring(Coupon coupon) {
		return coupon.getCouponDetail().getDuration() != 0
				&& coupon.getCouponDetail().getContract().getContractType() != -1 && coupon.getCouponDetail().getAmount() == 0;
	}

}
