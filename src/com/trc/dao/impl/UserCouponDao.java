package com.trc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.payment.coupon.UserCoupon;

@Repository
@SuppressWarnings("unchecked")
public class UserCouponDao extends HibernateDaoSupport {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  public void insertUserCoupon(UserCoupon userCoupon) {
    getHibernateTemplate().save(userCoupon);
  }

  public void deleteUserCoupon(UserCoupon userCoupon) {
    getHibernateTemplate().delete(userCoupon);
  }

  public void updateUserCoupon(UserCoupon userCoupon) {
    getHibernateTemplate().update(userCoupon);
  }

  public UserCoupon getUserCoupon(UserCoupon userCoupon) {
    return getHibernateTemplate().get(UserCoupon.class, userCoupon.getId());
  }

  public List<UserCoupon> getUserCoupons(int userId) {
    List<UserCoupon> userCoupons = getHibernateTemplate().find("from UserCoupon uc where uc.id.userId = ?", userId);
    return userCoupons;
  }

  public List<UserCoupon> getUserCoupons(int userId, int couponId) {
    List<UserCoupon> userCoupons = getHibernateTemplate().find("from UserCoupon uc where uc.userId = ? and uc", userId);
    return userCoupons;
  }
}
