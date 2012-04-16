package com.trc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import com.trc.exception.management.PaymentManagementException;
import com.trc.manager.PaymentManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.payment.refund.PaymentRefund;
import com.trc.web.model.ResultModel;
import com.trc.web.session.SessionManager;
import com.trc.web.session.SessionToken;
import com.tscp.mvne.PaymentTransaction;

@Controller
@RequestMapping("/payment/refund")
public class RefundController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private PaymentManager paymentManager;

  @ModelAttribute
  private PaymentRefund getPaymentRefund(@RequestParam int userId, @RequestParam int transId) {
    PaymentRefund paymentRefund = new PaymentRefund();
    try {
      PaymentTransaction paymentTransaction = paymentManager.getPaymentTransaction(userId, transId);
      paymentRefund.setPaymentTransaction(paymentTransaction);
      paymentRefund.setSessionToken(SessionManager.createToken("refund transaction " + transId));
      return paymentRefund;
    } catch (PaymentManagementException e) {
      return paymentRefund;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger("devLogger");

  @RequestMapping(value = "{transId}", method = RequestMethod.GET)
  public ModelAndView showRefund(@PathVariable int transId) {
    ResultModel resultModel = new ResultModel("payment/refund/confirm");
    User user = userManager.getCurrentUser();
    // try {
    // PaymentTransaction paymentTransaction =
    // paymentManager.getPaymentTransaction(user.getUserId(), transId);
    // PaymentRefund paymentRefund = new PaymentRefund();
    // paymentRefund.setPaymentTransaction(paymentTransaction);
    // paymentRefund.setSessionToken(SessionManager.createToken("refund transaction "
    // + transId));
    // resultModel.addObject("paymentRefund", paymentRefund);
    PaymentRefund paymentRefund = getPaymentRefund(user.getUserId(), transId);
    resultModel.addObject("sessionToken", paymentRefund.getSessionToken());
    return resultModel.getSuccess();
    // } catch (PaymentManagementException e) {
    // return resultModel.getAccessException();
    // }
  }

  @RequestMapping(value = "{transId}", method = RequestMethod.POST)
  public ModelAndView processRefund(HttpServletRequest request, @ModelAttribute PaymentRefund paymentRefund, @ModelAttribute SessionToken sessionToken,
      BindingResult result) {
    ResultModel resultModel = new ResultModel("redirect:/payment/history", "payment/refund/confirm");
    User user = userManager.getCurrentUser();
    SessionToken token = SessionManager.fetchToken(sessionToken.getId());
    if (token == null) {
      return resultModel.getAccessException();
    } else {
      token.consume();
      if (!SimpleImageCaptchaServlet.validateResponse(request, request.getParameter("jcaptcha"))) {
        result.rejectValue("jcaptcha", "jcaptcha.incorrect", "Image Verificaiton failed");
      }
      if (result.hasErrors()) {
        logger.debug("errors found with jcaptcha");
        logger.debug(Integer.toString(result.getErrorCount()));
        logger.debug(result.getAllErrors().toString());
        logger.debug(result.getFieldErrors().toString());
        return resultModel.getError();
      } else {
        try {
          paymentManager.refundPayment(paymentRefund.getPaymentTransaction().getAccountNo(), paymentRefund.getPaymentTransaction().getPaymentAmount(), String
              .valueOf(paymentRefund.getPaymentTransaction().getBillingTrackingId()), user);
        } catch (PaymentManagementException e) {
          logger.debug("exception while refunding");
          return resultModel.getAccessException();
        }
        logger.debug("returning success");
        return resultModel.getSuccess();
      }
    }
  }
}
