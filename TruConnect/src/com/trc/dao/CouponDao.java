package com.trc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponDetail;
import com.trc.coupon.UserCoupon;
import com.trc.util.logger.DevLogger;

/**
 * This DAO handles transactions for Coupon.class, CouponDetail.class and
 * UserCoupon.class.
 * 
 */

@Repository
public class CouponDao extends HibernateDaoSupport {
	@Resource
	private DevLogger devLogger;

	/* *****************************************************************
	 * Initialization
	 * *****************************************************************
	 */

	@Autowired
	public void init(HibernateTemplate hibernateTemplate) {
		setHibernateTemplate(hibernateTemplate);
	}

	/* *****************************************************************
	 * Coupon DAO methods
	 * *****************************************************************
	 */

	public int insertCoupon(Coupon coupon) {
		return (Integer) getHibernateTemplate().save(coupon);
	}

	public void deleteCoupon(Coupon coupon) {
		getHibernateTemplate().delete(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		getHibernateTemplate().update(coupon);
	}

	public Coupon getCoupon(int couponId) {
		return getHibernateTemplate().get(Coupon.class, couponId);
	}

	public Coupon getCouponByCode(String couponCode) {
		devLogger.log("CouponDao querying for couponCode " + couponCode);
		List<Coupon> coupons = getHibernateTemplate().find("from Coupon c where c.couponCode = ?", couponCode);
		if (coupons.size() != 1) {
			devLogger.log("WARN: CouponDao found " + coupons.size() + " results");
			return null;
		} else {
			devLogger.log("Success: CouponDao found coupon " + coupons.get(0).getCouponId());
			return coupons.get(0);
		}
	}

	/* *****************************************************************
	 * CouponDetail DAO methods
	 * *****************************************************************
	 */

	public int insertCouponDetail(CouponDetail couponDetail) {
		return (Integer) getHibernateTemplate().save(couponDetail);
	}

	public void deleteCouponDetail(CouponDetail couponDetail) {
		getHibernateTemplate().delete(couponDetail);
	}

	public void updateCouponDetail(CouponDetail couponDetail) {
		getHibernateTemplate().update(couponDetail);
	}

	public CouponDetail getCouponDetail(int couponDetailId) {
		return getHibernateTemplate().get(CouponDetail.class, couponDetailId);
	}

	/* *****************************************************************
	 * UserCoupon DAO methods
	 * *****************************************************************
	 */

	public void insertUserCoupon(UserCoupon userCoupon) {
		getHibernateTemplate().save(userCoupon);
	}

	public void deleteUserCoupon(UserCoupon userCoupon) {
		getHibernateTemplate().delete(userCoupon);
	}

	public List<UserCoupon> getUserCoupons(UserCoupon userCoupon) {
		int userId = userCoupon.getId().getUserId();
		int couponId = userCoupon.getId().getCouponId();
		int accountNumber = userCoupon.getId().getAccountNumber();
		List<UserCoupon> userCoupons = getHibernateTemplate().find(
				"from UserCoupon uc where uc.id.userId = ? and uc.id.couponId = ? and uc.id.accountNumber = ?", userId,
				couponId, accountNumber);
		return userCoupons;
	}

}