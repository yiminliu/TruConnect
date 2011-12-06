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
import com.trc.exception.ValidationException;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.CouponManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;

@Component
public class CouponValidator implements Validator {
	@Autowired
	private CouponManager couponManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;
	@Resource
	private DevLogger devLogger;

	@Override
	public boolean supports(Class<?> myClass) {
		return Coupon.class.isAssignableFrom(myClass);
	}

	/**
	 * This is currently used by webflow only. It skips validation if no coupon is
	 * entered.
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Coupon coupon = (Coupon) target;
		checkExists(coupon, errors);
		if (!errors.hasErrors()) {
			checkCouponCode(coupon.getCouponCode(), errors);
			checkStartDate(coupon.getStartDate(), errors);
			checkEndDate(coupon.getEndDate(), errors);
			checkQuantity(coupon, errors);
		}
	}

	public void validate(Coupon coupon, int accountNumber, Errors errors) {
		checkExists(coupon, errors);
		if (!errors.hasErrors()) {
			try {
				User user = userManager.getCurrentUser();
				Account account = accountManager.getAccount(accountNumber);
				validate(coupon, errors);
				checkApplied(coupon, user, account, errors);
				checkLimit(coupon, user, account, errors);
				checkAccount(accountNumber, errors);
			} catch (AccountManagementException e) {
				errors.reject("coupon.device.required", "You must choose a device to apply the coupon to");
			}
		}
	}

	private void checkAccount(int accountNumber, Errors errors) {
		devLogger.log("...checking account number");
		if (accountNumber == 0) {
			devLogger.error("......error found with account number");
			errors.reject("coupon.device.required", "You must choose a device to apply the coupon to");
		}
	}

	private void checkCouponCode(String couponCode, Errors errors) {
		devLogger.log("...checking coupon code");
		if (couponCode == null || couponCode.length() < 3 || !couponCode.substring(0, 3).equals("tru")) {
			devLogger.error("......error found with coupon code");
			errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
		}
	}

	private void checkEndDate(Date endDate, Errors errors) {
		devLogger.log("...checking end date");
		if (endDate != null && endDate.compareTo(new Date()) <= 0) {
			devLogger.error("......error found with end date");
			errors.rejectValue("endDate", "coupon.date.expired", "Coupon is expired");
		}
	}

	private void checkStartDate(Date startDate, Errors errors) {
		devLogger.log("...checking start date");
		if (startDate != null && startDate.compareTo(new Date()) >= 0) {
			devLogger.error("......error found with start date");
			errors.rejectValue("startDate", "coupon.date.notActive", "Coupon has not started");
		}
	}

	private void checkQuantity(Coupon coupon, Errors errors) {
		devLogger.log("...checking quantity");
		if (coupon.getQuantity() > 0 && (coupon.getQuantity() - coupon.getUsed()) < 1) {
			devLogger.error("......error found with quantity");
			errors.rejectValue("quantity", "coupon.quantity.insufficient", "This coupon has been exhausted");
		}
	}

	private void checkExists(Coupon coupon, Errors errors) {
		devLogger.log("...checking if coupon exists");
		if (coupon == null) {
			devLogger.error("......error: coupon is null");
			errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
		} else if (coupon.isEmpty()) {
			devLogger.error("......error: coupon code is empty");
			errors.rejectValue("couponCode", "coupon.code.required", "You must enter a coupon code");
		} else {
			try {
				Coupon fetchedCoupon = couponManager.getCouponByCode(coupon.getCouponCode());
				if (fetchedCoupon == null || fetchedCoupon.isEmpty()) {
					errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
				} else {
					coupon = fetchedCoupon;
				}
			} catch (CouponManagementException e) {
				errors.rejectValue("couponCode", "coupon.code.invalid", "Not a valid coupon code");
			}
		}
	}

	private void checkApplied(Coupon coupon, User user, Account account, Errors errors) {
		devLogger.log("...checking if coupon is applied");
		try {
			if (isApplied(coupon, user, account)) {
				devLogger.error("......error: coupon already applied");
				errors.reject("coupon.applied", "Coupon has already been applied to your account");
			}
		} catch (ValidationException e) {
			// this error is caught by checkCouponCode
		}
	}

	private void checkLimit(Coupon coupon, User user, Account account, Errors errors) {
		devLogger.log("...checking account limit");
		if (isAtAccountLimit(coupon, user, account)) {
			devLogger.error("......error with account limit");
			errors.reject("coupon.limit", "You cannot apply anymore coupons of this type");
		}
	}

	public boolean isEligible(Coupon coupon, User user, Account account) {
		try {
			boolean isAvailable = isAvailable(coupon);
			boolean isApplied = isApplied(coupon, user, account);
			boolean isAtAccountLimit = isAtAccountLimit(coupon, user, account);
			boolean isExistingStackable;
			boolean isStackable;
			List<UserCoupon> userCoupons = couponManager.getUserCoupons(user.getUserId());
			Coupon existingCoupon;
			for (UserCoupon uc : userCoupons) {
				existingCoupon = uc.getId().getCoupon();
				isExistingStackable = existingCoupon.isStackable();
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
		} catch (ValidationException e) {
			return false;
		}
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

	public boolean isApplied(Coupon coupon, User user, Account account) throws ValidationException {
		try {
			UserCoupon userCoupon = couponManager.getUserCoupon(new UserCoupon(coupon, user, account));
			return userCoupon != null;
		} catch (CouponManagementException e) {
			throw new ValidationException(e.getMessage(), e.getCause());
		}
	}

	public boolean isAtAccountLimit(Coupon coupon, User user, Account account) {
		devLogger.log("......checking account limit");
		int accountLimit = coupon.getCouponDetail().getAccountLimit() == null ? -1 : coupon.getCouponDetail()
				.getAccountLimit();
		devLogger.log("......account limit is " + accountLimit);
		if (accountLimit == -1) {
			return false;
		}
		int count = 0;
		try {
			List<UserCoupon> userCoupons = couponManager.getUserCoupons(user.getUserId());
			for (UserCoupon userCoupon : userCoupons) {
				if (userCoupon.getId().getCoupon().getCouponDetail().getDetailType().getDetailType() == coupon
						.getCouponDetail().getDetailType().getDetailType()) {
					count++;
				}
			}
			devLogger.log("......final count of userCoupons is " + count + ". Result of count >= accountLimit is "
					+ (count >= accountLimit));
			return count >= accountLimit;
		} catch (CouponManagementException e) {
			devLogger.error("......an error occured while fetching userCoupons");
			return true;
		}
	}

}