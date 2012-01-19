package com.trc.dao.impl;

import org.springframework.stereotype.Repository;

import com.trc.coupon.CouponDetail;

@Repository
public class CouponDetailDao extends AbstractHibernateDao<CouponDetail> {

  public CouponDetailDao() {
    setClazz(CouponDetail.class);
  }
}
