package com.trc.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.trc.coupon.Coupon;

/**
 * This DAO handles transactions for Coupon.class, CouponDetail.class and
 * UserCoupon.class.
 * 
 */

@Repository
@SuppressWarnings("unchecked")
public class CouponDao extends AbstractHibernateDao<Coupon> {

  public CouponDao() {
    setClazz(Coupon.class);
  }

  public Coupon getCouponByCode(String couponCode) {
    Query query = getCurrentSession().createQuery("from Coupon where couponCode = :couponCode");
    query.setString("couponCode", couponCode);
    List<Coupon> results = query.list();
    if (isUniqueResult(results)) {
      return getUniqueResult(results);
    } else {
      return null;
    }
  }

}