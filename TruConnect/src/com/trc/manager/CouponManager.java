package com.trc.manager;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.UserCoupon;
import com.trc.exception.management.CouponManagementException;
import com.trc.exception.service.CouponServiceException;
import com.trc.service.CouponService;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.KenanContract;
import com.tscp.mvne.ServiceInstance;

@Component
public class CouponManager {
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponValidator couponValidator;
	@Resource
	private DevLogger devLogger;

	public void applyCouponPayment(Account account, Double amount) throws CouponManagementException {
		// TODO coupon application should really just take a coupon, the account and
		// the service instance
		try {
			devLogger.log("..applying coupon payment on account " + account.getAccountno() + " for " + amount);
			couponService.applyCouponPayment(account, amount, new Date());
		} catch (CouponServiceException e) {
			throw new CouponManagementException(e.getMessage(), e.getCause());
		}
	}

	public List<Coupon> getCoupons(Account account, ServiceInstance serviceInstance) throws CouponManagementException {
		try {
			// TODO convert contracts to coupons
			List<Coupon> coupons = new Vector<Coupon>();
			List<KenanContract> contracts = couponService.getContracts(account, serviceInstance);
			return coupons;
		} catch (CouponServiceException e) {
			throw new CouponManagementException(e.getMessage(), e.getCause());
		}
	}

	@Loggable(value = LogLevel.TRACE)
	public boolean applyCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
			throws CouponManagementException {
		if (!couponValidator.checkUsed(coupon, user, account)) {
			try {
				devLogger.log("Inserting UserCoupon and applying contract in Kenan");
				couponService.applyCoupon(user, coupon, account, serviceInstance);
				return true;
			} catch (CouponServiceException e) {
				// TODO clean-up exception and rollback. only cancel if coupon was
				// actually applied.
				cancelCoupon(coupon, user, account, serviceInstance);
				throw new CouponManagementException(e.getMessage(), e.getCause());
			}
		} else {
			throw new CouponManagementException("Coupon " + coupon.getCouponId() + " has already been applied by user "
					+ user.getUserId());
		}
	}

	@Loggable(value = LogLevel.TRACE)
	public boolean cancelCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
			throws CouponManagementException {
		try {
			devLogger.log("Canceling Coupon");
			couponService.cancelCoupon(user, coupon, account, serviceInstance);
			return true;
		} catch (CouponServiceException e) {
			// TODO should attempt to cancel again or queue for cancelation
			throw new CouponManagementException(e.getMessage(), e.getCause());
		}
	}

	/* *****************************************************************
	 * UserCoupon Management
	 * *****************************************************************
	 */

	public List<UserCoupon> getUserCoupon(Coupon coupon, User user, Account account) throws CouponManagementException {
		return couponService.getUserCoupon(user, coupon, account);
	}

	public List<UserCoupon> getUserCoupons(int userId) {
		return couponService.getUserCoupons(userId);
	}

	/* *****************************************************************
	 * Coupon Management
	 * *****************************************************************
	 */

	public int insertCoupon(Coupon coupon) {
		return couponService.insertCoupon(coupon);
	}

	public void deleteCoupon(Coupon coupon) {
		couponService.deleteCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		couponService.updateCoupon(coupon);
	}

	public Coupon getCoupon(int couponId) {
		return couponService.getCoupon(couponId);
	}

	public Coupon getCouponByCode(String couponCode) {
		devLogger.log("CouponManager.getCouponByCode");
		return couponService.getCouponByCode(couponCode);
	}

	/* *****************************************************************
	 * CouponDetail Management
	 * *****************************************************************
	 */

	public int insertCouponDetail(CouponDetail couponDetail) {
		return couponService.insertCouponDetail(couponDetail);
	}

	public void deleteCouponDetail(CouponDetail couponDetail) {
		couponService.deleteCouponDetail(couponDetail);
	}

	public void updateCouponDetail(CouponDetail couponDetail) {
		couponService.updateCouponDetail(couponDetail);
	}

	public CouponDetail getCouponDetail(int couponDetailId) {
		return couponService.getCouponDetail(couponDetailId);
	}
}