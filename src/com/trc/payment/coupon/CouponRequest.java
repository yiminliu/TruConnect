package com.trc.payment.coupon;

import com.trc.web.session.SessionObject;
import com.tscp.mvne.Account;

public class CouponRequest extends SessionObject {
  private static final long serialVersionUID = -350728886862006180L;
  private Coupon coupon;
  private Account account;

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Coupon getCoupon() {
    return coupon;
  }

  public void setCoupon(Coupon coupon) {
    this.coupon = coupon;
  }

}