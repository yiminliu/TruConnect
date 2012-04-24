package com.trc.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.payment.coupon.CouponDetail;
import com.trc.payment.coupon.CouponStackable;
import com.trc.payment.coupon.CouponStackableId;

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

  /*
   * public int insertCouponStackable(int couponDetailId, CouponDetail
   * couponDetail) {
   * 
   * HibernateTemplate ht = getHibernateTemplate(); int stackableWithId;
   * 
   * String columns = "coupon_detail_id, stackable_with"; String values = "";
   * String queryString = ""; /// final String hql = ""; int detailType =
   * couponDetail.getDetailType().getDetailType();
   * if(couponDetail.getDetailType().getDetailType() == 1) {//Repeating MRC
   * Coupon -- stackable with one-time coupon, but not repeating MRC coupons
   * //queryString =
   * "insert into couponStackable(couponDetailId, select cd.couponDetailId from couponDetail cd where cd.detailType.detailType != "
   * + detailType; String query =
   * "from CouponDetail cd where cd.detailType.detailType != " + detailType;
   * List<CouponDetail> cdList = getHibernateTemplate().find(query);
   * List<CouponStackable> csList = new ArrayList<CouponStackable>();
   * 
   * CouponStackable cs = new CouponStackable(); CouponStackableId csi = new
   * CouponStackableId(); cs.setId(csi); for(CouponDetail cd : cdList) {
   * cs.getId().setCouponDetail(couponDetail);
   * cs.getId().setStackableCouponDetailId(cd.getCouponDetailId());
   * //csList.add(cs); ht.save(cs); }
   * 
   * } if(couponDetail.getDetailType().getDetailType() == 2) {//One Time Payment
   * Coupon //values = "couponDetailId, 2";
   * 
   * // queryString =
   * "insert into couponStackable (couponDetailId, select cd.couponDetailId from couponDetail cd "
   * + // "where cd.couponDetailId != " + detailType; // queryString =
   * "insert into CouponStackable select "+couponDetailId +
   * ", cd.couponDetailId from couponDetail cd " + //
   * "where cd.detailType.detailType != " + detailType;
   * 
   * final String hql =
   * "insert into CouponStackable (id.couponDetail.couponDetailId, id.couponDetail.stackableCouponDetailId) select "
   * + couponDetailId + ", cd.couponDetailId from couponDetail cd";//} //
   * "where cd.detailType.detailType != " + detailType;
   * 
   * //final String hql = "insert into CouponStackable(c values("+couponDetailId
   * + ", select cd.couponDetailId from couponDetail cd " + //
   * "where cd.detailType.detailType != " + detailType; // final String hql =
   * "insert into CouponStackable(" + columns + ") values (" + values + ")";
   * 
   * Integer updates = (Integer) getHibernateTemplate().execute(new
   * HibernateCallback() { public Object doInHibernate(Session session) throws
   * HibernateException,SQLException { int updts =
   * session.createQuery(hql).executeUpdate(); return new Integer(updts); } });
   * } return 0;
   * 
   * //return getHibernateTemplate().bulkUpdate(queryString);
   * 
   * }
   */
}