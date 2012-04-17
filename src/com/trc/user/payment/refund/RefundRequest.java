package com.trc.user.payment.refund;

import com.trc.web.session.SessionObject;
import com.tscp.mvne.PaymentTransaction;

public class RefundRequest extends SessionObject {
  private static final long serialVersionUID = -904882266690809131L;
  private PaymentTransaction paymentTransaction;
  private String jcaptcha;

  public PaymentTransaction getPaymentTransaction() {
    return paymentTransaction;
  }

  public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
    this.paymentTransaction = paymentTransaction;
  }

  public String getJcaptcha() {
    return jcaptcha;
  }

  public void setJcaptcha(String jcaptcha) {
    this.jcaptcha = jcaptcha;
  }

}
