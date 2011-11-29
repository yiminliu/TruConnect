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
@SuppressWarnings("unchecked")
public class CouponDao extends HibernateDaoSupport {

	@Autowired
	public void init(HibernateTemplate hibernateTemplate) {
		setHibernateTemplate(hibernateTemplate);
	}

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
		List<Coupon> coupons = getHibernateTemplate().find("from Coupon c where c.couponCode = ?", couponCode);
		if (coupons.size() != 1) {
			return null;
		} else {
			return coupons.get(0);
		}
	}

}