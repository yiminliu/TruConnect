package com.trc.payment.coupon;

import com.trc.web.session.SessionObject;

public class CouponRequest extends SessionObject {
  private static final long serialVersionUID = -350728886862006180L;
  private Coupon coupon;

  public Coupon getCoupon() {
    return coupon;
  }

  public void setCoupon(Coupon coupon) {
    this.coupon = coupon;
  }

}