package com.trc.dao;

import java.util.List;

import com.trc.coupon.UserCoupon;
import com.trc.coupon.UserCouponId;

public interface UserCouponDaoModel {

  public abstract UserCoupon getById(UserCouponId userCouponId);

  public abstract UserCoupon getUserCoupon(UserCoupon userCoupon);

  public abstract List<UserCoupon> getByUserId(Integer userId);

}