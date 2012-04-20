package com.trc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.exception.management.PaymentManagementException;
import com.trc.manager.impl.PaymentManager;
import com.trc.manager.impl.UserManager;
import com.trc.user.User;
import com.trc.user.payment.refund.RefundRequest;
import com.trc.web.model.ResultModel;
import com.trc.web.session.SessionManager;
import com.trc.web.session.SessionRequest;
import com.trc.web.session.SessionToken;
import com.trc.web.validation.JcaptchaValidator;
import com.tscp.mvne.PaymentTransaction;

@Controller
@RequestMapping("/payment/refund")
public class RefundController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private PaymentManager paymentManager;

  @RequestMapping(value = "{transId}", method = RequestMethod.GET)
  public ModelAndView showRefund(@PathVariable int transId) {
    ResultModel resultModel = new ResultModel("payment/refund/confirm");
    User user = userManager.getCurrentUser();
    try {
      PaymentTransaction paymentTransaction = paymentManager.getPaymentTransaction(user.getUserId(), transId);
      RefundRequest refundRequest = new RefundRequest();
      refundRequest.setPaymentTransaction(paymentTransaction);
      refundRequest.setSessionToken(SessionManager.createToken(SessionRequest.REFUND, "refund transaction " + transId));
      resultModel.addObject("paymentRefund", refundRequest);
      return resultModel.getSuccess();
    } catch (PaymentManagementException e) {
      return resultModel.getAccessException();
    }
  }

  @RequestMapping(value = "{transId}", method = RequestMethod.POST)
  public ModelAndView processRefund(HttpServletRequest request, @ModelAttribute RefundRequest refundRequest, BindingResult result) {
    ResultModel resultModel = new ResultModel("redirect:/account/payment/history", "payment/refund/confirm");
    User user = userManager.getCurrentUser();
    SessionToken token = SessionManager.fetchToken(refundRequest.getSessionToken().getRequest());
    if (token != null && token.getId().equals(refundRequest.getSessionToken().getId())) {
      JcaptchaValidator.validate(request, result);
      if (result.hasErrors()) {
        return resultModel.getError();
      } else {
        token.consume();
        try {
          paymentManager.refundPayment(user, refundRequest);
        } catch (PaymentManagementException e) {
          return resultModel.getAccessException();
        }
        return resultModel.getSuccess();
      }
    } else {
      return resultModel.getAccessException();
    }
  }
}
