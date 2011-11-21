package com.trc.coupon.validator;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import com.trc.coupon.Coupon;
import com.trc.coupon.UserCoupon;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.CouponManager;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;

@Component
public class CouponValidator extends HibernateDaoSupport {
	@Autowired
	private CouponManager couponManager;
	@Resource
	private DevLogger devLogger;

	@Autowired
	public void init(HibernateTemplate hibernateTemplate) {
		setHibernateTemplate(hibernateTemplate);
	}

	public boolean validateCoupon(Coupon coupon, User user, Account account) {
		return validateCoupon(coupon) && !couponUsed(coupon, user, account);
	}

	private boolean validateCoupon(Coupon coupon) {
		return couponExists(coupon) && coupon.isEnabled() && validateCouponCode(coupon.getCouponCode())
				&& validateEndDate(coupon.getEndDate()) && validateStartDate(coupon.getStartDate());
	}

	private boolean validateCouponCode(String couponCode) {
		// TODO there are no specifications as to what coupon codes will look like.
		// This assumes that all coupons will be a string beginning with "tru"
		if (couponCode != null) {
			return couponCode.substring(0, 3).equals("tru");
		} else {
			return false;
		}
	}

	private boolean validateEndDate(Date endDate) {
		return (endDate == null || endDate.compareTo(new Date()) >= 0);
	}

	private boolean validateStartDate(Date startDate) {
		return (startDate != null && startDate.compareTo(new Date()) <= 0);
	}

	public boolean couponExists(Coupon coupon) {
		return coupon != null;
	}

	public boolean couponUsed(Coupon coupon, User user, Account account) {
		devLogger.log("Checking if coupon has already been redeemed...");
		try {
			List<UserCoupon> userCoupons = couponManager.getUserCoupons(coupon, user, account);
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
