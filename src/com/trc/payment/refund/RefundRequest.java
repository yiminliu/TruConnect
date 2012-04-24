package com.trc.payment.refund;

import com.trc.web.session.SessionObject;
import com.tscp.mvne.PaymentTransaction;

public class RefundRequest extends SessionObject {
  private static final long serialVersionUID = -904882266690809131L;
  private PaymentTransaction paymentTransaction;
  private RefundCode refundCode;
  private String jcaptcha;
  private String notes;

  public String getJcaptcha() {
    return jcaptcha;
  }

  public String getNotes() {
    return notes;
  }

  public PaymentTransaction getPaymentTransaction() {
    return paymentTransaction;
  }

  public RefundCode getRefundCode() {
    return refundCode;
  }

  public void setJcaptcha(String jcaptcha) {
    this.jcaptcha = jcaptcha;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
    this.paymentTransaction = paymentTransaction;
  }

  public void setRefundCode(RefundCode refundCode) {
    this.refundCode = refundCode;
  }

}
