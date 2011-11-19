package com.trc.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.validator.CouponValidator;
import com.trc.dao.CouponDao;
import com.trc.dao.CouponDetailDao;
import com.trc.exception.management.CouponManagementException;
import com.trc.exception.service.CouponServiceException;
import com.trc.service.CouponService;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Component
public class CouponManager {
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponValidator couponValidator;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private CouponDetailDao couponDetailDao;

	public int insertCouponDetail(CouponDetail couponDetail) {
		return couponDetailDao.insertCouponDetail(couponDetail);
	}

	public void deleteCouponDetail(CouponDetail couponDetail) {
		couponDetailDao.deleteCouponDetail(couponDetail);
	}

	public void updateCouponDetail(CouponDetail couponDetail) {
		couponDetailDao.updateCouponDetail(couponDetail);
	}

	public CouponDetail getCouponDetail(int couponDetailId) {
		return couponDetailDao.getCouponDetail(couponDetailId);
	}

	public int insertCoupon(Coupon coupon) {
		return couponDao.insertCoupon(coupon);
	}

	public void deleteCoupon(Coupon coupon) {
		couponDao.deleteCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		couponDao.updateCoupon(coupon);
	}

	public Coupon getCoupon(int couponId) {
		return couponDao.getCoupon(couponId);
	}

	public Coupon getCouponByCode(String couponCode) {
		return couponDao.getCouponByCode(couponCode);
	}

	public boolean cancelCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
			throws CouponManagementException {
		try {
			couponDao.deleteUserCoupon(user, coupon, account);
			couponService.cancelCoupon(coupon, account, serviceInstance);
			return true;
		} catch (CouponServiceException e) {
			// TODO should attempt to cancel again or queue for cancelation
			throw new CouponManagementException(e.getMessage(), e.getCause());
		}
	}

	public boolean redeemCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
			throws CouponManagementException {
		if (validateCoupon(coupon, user, account)) {
			return applyCoupon(coupon, user, account, serviceInstance);
		} else {
			return false;
		}
	}

	private boolean validateCoupon(Coupon coupon, User user, Account account) {
		return couponValidator.couponExists(coupon) && couponValidator.validateCoupon(coupon, user, account);
	}

	private boolean applyCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
			throws CouponManagementException {
		try {
			couponDao.insertUserCoupon(user, coupon, account);
			couponService.redeemCoupon(coupon, account, serviceInstance);
			return true;
		} catch (CouponServiceException e) {
			// TODO clean-up exception and rollback
			cancelCoupon(coupon, user, account, serviceInstance);
			throw new CouponManagementException(e.getMessage(), e.getCause());
		}
	}
}