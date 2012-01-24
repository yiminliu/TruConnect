package com.trc.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.trc.coupon.UserCoupon;
import com.trc.coupon.UserCouponId;
import com.trc.dao.UserCouponDaoModel;

@Repository
@SuppressWarnings("unchecked")
public class UserCouponDao extends AbstractHibernateDao<UserCoupon> implements UserCouponDaoModel {

  public UserCouponDao() {
    setClazz(UserCoupon.class);
  }

  /* (non-Javadoc)
   * @see com.trc.dao.impl.UserCouponDaoModel#getById(com.trc.coupon.UserCouponId)
   */
  @Override
  public UserCoupon getById(UserCouponId userCouponId) {
    Preconditions.checkArgument(userCouponId != null);
    return (UserCoupon) this.getCurrentSession().get(UserCoupon.class, userCouponId);
  }

  /* (non-Javadoc)
   * @see com.trc.dao.impl.UserCouponDaoModel#getUserCoupon(com.trc.coupon.UserCoupon)
   */
  @Override
  public UserCoupon getUserCoupon(UserCoupon userCoupon) {
    return getById(userCoupon.getId());
  }

  /* (non-Javadoc)
   * @see com.trc.dao.impl.UserCouponDaoModel#getByUserId(java.lang.Integer)
   */
  @Override
  public List<UserCoupon> getByUserId(Integer userId) {
    Preconditions.checkArgument(userId != null);
    Query query = this.getCurrentSession().createQuery("from UserCoupon uc where uc.id.userId = :userId");
    query.setInteger("userId", userId);
    return query.list();
  }
}
