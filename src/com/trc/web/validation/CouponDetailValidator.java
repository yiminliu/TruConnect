package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.payment.coupon.CouponDetail;

@Component
public class CouponDetailValidator implements Validator {

  @Override
  public boolean supports(Class<?> myClass) {
    return CouponDetail.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CouponDetail couponDetail = (CouponDetail) target;
    checkContractType(couponDetail.getContract().getContractType(), errors);
    if (couponDetail.getDetailType().getDetailType() == 1) {
      checkDuration(couponDetail.getDuration(), errors);
      checkDurationUnit(couponDetail.getDurationUnit(), errors);
    } else if (couponDetail.getDetailType().getDetailType() == 1) {
      checkAmount(couponDetail.getAmount(), errors);
    } else {
      errors.reject("couponDetail.detailType", "Coupon type must be set");
    }
  }

  protected void checkContractType(int type, Errors errors) {
    if (type == 0) {
      errors.rejectValue("contract.contractType", "contract.type.required", "You must select a contract type");
    }
  }

  protected void checkDuration(int duration, Errors errors) {
    if (duration <= 0) {
      errors.rejectValue("duration", "couponDetail.duration.required", "You must set a specified duration");
    }
  }

  protected void checkDurationUnit(String unit, Errors errors) {
    if (!unit.equals("DAY") && !unit.equals("MONTH")) {
      errors.rejectValue("durationtUnit", "couponDetail.duration.unit.required", "You must specify a duration unit (months or days)");
    }
  }

  protected void checkAmount(double amount, Errors errors) {
    if (amount <= 0.0) {
      errors.rejectValue("amount", "couponDetail.amount.required", "You must set an amount for the coupon");
    }
  }

}