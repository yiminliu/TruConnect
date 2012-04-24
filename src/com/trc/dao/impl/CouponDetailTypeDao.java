package com.trc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.payment.coupon.CouponDetailType;

@Repository
public class CouponDetailTypeDao extends HibernateDaoSupport {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  public int insertCouponDetailType(CouponDetailType couponDetailType) {
    return (Integer) getHibernateTemplate().save(couponDetailType);
  }

  public void deleteCouponDetailType(CouponDetailType couponDetailType) {
    getHibernateTemplate().delete(couponDetailType);
  }

  public void updateCouponDetailType(CouponDetailType couponDetailType) {
    getHibernateTemplate().update(couponDetailType);
  }

  public CouponDetailType getCouponDetailType(int couponDetailId) {
    return getHibernateTemplate().get(CouponDetailType.class, couponDetailId);
  }

  public List<CouponDetailType> getAllCouponDetailTypes() {
    return getHibernateTemplate().find("from CouponDetailType order by description");
  }
}