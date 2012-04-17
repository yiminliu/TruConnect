package com.trc.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trc.coupon.Coupon;
import com.trc.coupon.CouponRequest;
import com.trc.coupon.ajax.CouponResponse;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.CouponManager;
import com.trc.manager.UserManager;
import com.trc.security.encryption.SessionEncrypter;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.web.model.ResultModel;
import com.trc.web.session.SessionManager;
import com.trc.web.session.SessionRequest;
import com.trc.web.session.SessionToken;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Controller
@RequestMapping("/coupons")
public class CouponController {
  private static final Logger errorLogger = LoggerFactory.getLogger(CouponController.class);
  private static final Logger devLogger = LoggerFactory.getLogger("devLogger");
  @Autowired
  private CouponManager couponManager;
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;
  @Autowired
  private CouponValidator couponValidator;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
  @RequestMapping(value = "/validate", method = RequestMethod.GET)
  public @ResponseBody
  CouponResponse validateCoupon(@RequestParam String couponCode) {
    try {
      Coupon coupon = couponManager.getCouponByCode(couponCode);
      if (coupon != null) {
        if (coupon.getCouponDetail().getContract().getContractType() > 0) {
          return new CouponResponse(true, coupon.getCouponDetail().getContract().getDescription() + " for " + coupon.getCouponDetail().getDuration()
              + " months");
        } else {
          return new CouponResponse(true, "Credit value of $" + coupon.getCouponDetail().getAmount());
        }
      } else {
        return new CouponResponse(false, "Not a valid coupon");
      }
    } catch (CouponManagementException e) {
      return new CouponResponse(false, "Internal error, try again");
    }
  }

  // TODO this is very inefficient as it fetches the entire overview just to
  // list the accounts and the device names to display. Many objects on the
  // TSCPMVNE side need to be heavily remodeled.
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showRedeemCoupon() {
    ResultModel model = new ResultModel("coupon/addCoupon");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    List<AccountDetail> accountList = overview.getAccountDetails();

    CouponRequest couponRequest = new CouponRequest();
    couponRequest.setCoupon(new Coupon());
    couponRequest.setSessionToken(SessionManager.createToken(SessionRequest.COUPON, "coupon request for user " + user.getUserId()));

    devLogger.debug("Coupon request page generated {}", (SessionToken) SessionManager.get(SessionRequest.COUPON));

    model.addObject("couponRequest", couponRequest);
    model.addObject("accountList", accountList);
    return model.getSuccess();
  }

  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView postRedeemCoupon(HttpServletRequest request, @RequestParam("account") String encodedAccountNo,
      @ModelAttribute CouponRequest couponRequest, BindingResult result) {
    ResultModel model = new ResultModel("coupon/addCouponSuccess", "coupon/addCoupon");
    User user = userManager.getCurrentUser();
    int accountNumber = 0;

    if (encodedAccountNo != null) {
      accountNumber = SessionEncrypter.decryptId(encodedAccountNo);
    } else {
      errorLogger.error("Cannot apply coupon for {}. Account number not set.", user);
      return model.getAccessException();
    }

    SessionToken token = SessionManager.fetchToken(couponRequest.getSessionToken().getRequest());
    if (token != null && token.getId().equals(couponRequest.getSessionToken().getId())) {
      try {
        couponRequest.setCoupon(couponManager.getCouponByCode(couponRequest.getCoupon().getCouponCode()));
        couponValidator.validate(couponRequest.getCoupon(), accountNumber, result);
        if (result.hasErrors()) {
          model.addObject("accountList", accountManager.getOverview(user).encodeAccountNo().getAccountDetails());
          return model.getError();
        } else {
          try {
            Account account = accountManager.getAccount(accountNumber);
            ServiceInstance serviceInstance = null;
            if (account != null && account.getServiceinstancelist() != null && account.getServiceinstancelist().size() > 0) {
              serviceInstance = account.getServiceinstancelist().get(0);
              if (serviceInstance != null) {
                couponManager.applyCoupon(couponRequest.getCoupon(), user, account, serviceInstance);
              } else {
                return model.getAccessException();
              }
            }
          } catch (AccountManagementException e) {
            model.getAccessException();
          }
          List<AccountDetail> accountList = accountManager.getOverview(user).getAccountDetails();
          AccountDetail accountDetail = null;
          for (AccountDetail ad : accountList) {
            if (ad.getAccount().getAccountno() == accountNumber) {
              accountDetail = ad;
            }
          }
          model.addObject("accountDetail", accountDetail);
          model.addObject("coupon", couponRequest);
          return model.getSuccess();
        }
      } catch (CouponManagementException e) {
        return model.getAccessException();
      }
    } else {
      errorLogger.error("Cannot apply coupon for {}. Session token does not exist or does not match.", user);
      return model.getAccessException();
    }

  }
}