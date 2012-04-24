package com.trc.payment.refund;

public enum RefundCode {
  UNSELECTED(0, "Select one..."), UNNECESSARY_TOPUP(1, "Unneccessary top-up"), DOUBLE_TOPUP(2, "Double top-up");

  private int value;
  private String description;

  private RefundCode(int value, String description) {
    this.value = value;
    this.description = description;
  }

  public int getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }

}
