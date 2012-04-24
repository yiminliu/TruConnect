package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.payment.refund.RefundCode;
import com.trc.payment.refund.RefundRequest;

@Component
public class RefundRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> myClass) {
    return RefundRequest.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    RefundRequest refund = (RefundRequest) target;
    checkRefundCode(refund.getRefundCode(), errors);
  }

  protected void checkRefundCode(RefundCode refundCode, Errors errors) {
    if (refundCode.equals(RefundCode.UNSELECTED)) {
      errors.rejectValue("refundCode", "payment.refund.refundCode.required", "You must choose a refund code");
    }
  }

}
