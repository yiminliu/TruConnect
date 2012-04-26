package com.trc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.payment.coupon.CouponDetail;
import com.trc.payment.coupon.CouponStackable;
import com.trc.payment.coupon.CouponStackableId;
import com.trc.util.logger.DevLogger;

@Repository
public class CouponDetailDao extends HibernateDaoSupport {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

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

  public List<CouponDetail> getAllCouponDetails() {
    return getHibernateTemplate().find("from CouponDetail order by contract.description, duration, durationUnit");
  }

  public void insertCouponStackable(CouponDetail couponDetail) {
    String query = "";
    List<CouponDetail> cdList = null;
    if (couponDetail.getDetailType().getDetailType() == 1) {// Repeating MRC
                                                            // Coupon --
                                                            // stackable with
                                                            // one-time coupon,
                                                            // but not repeating
                                                            // MRC coupons
      query = "from CouponDetail cd where cd.detailType.detailType != " + couponDetail.getDetailType().getDetailType();
    } else if (couponDetail.getDetailType().getDetailType() == 2) {// One Time
                                                                   // Payment
                                                                   // Coupon
      query = "from CouponDetail cd where cd.couponDetailId != " + couponDetail.getCouponDetailId();

    }
    DevLogger.log("COUPONDETAIL: {}", couponDetail);
    DevLogger.log("query: {}", query);
    cdList = getHibernateTemplate().find(query);
    for (CouponDetail cd : cdList) {
      CouponStackable cs = new CouponStackable();
      CouponStackableId csi = new CouponStackableId();
      csi.setCouponDetail(couponDetail);
      csi.setStackableCouponDetailId(cd.getCouponDetailId());
      cs.setId(csi);
      getHibernateTemplate().save(cs);
    }
  }
}