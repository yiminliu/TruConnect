package com.trc.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.impl.AccountManager;
import com.trc.manager.impl.CouponManager;
import com.trc.manager.impl.UserManager;
import com.trc.payment.coupon.Coupon;
import com.trc.payment.coupon.CouponDetail;
import com.trc.payment.coupon.CouponDetailType;
import com.trc.payment.coupon.CouponRequest;
import com.trc.payment.coupon.ajax.CouponResponse;
import com.trc.payment.coupon.contract.Contract;
import com.trc.security.encryption.SessionEncrypter;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.util.logger.DevLogger;
import com.trc.web.model.ResultModel;
import com.trc.web.session.SessionManager;
import com.trc.web.session.SessionRequest;
import com.trc.web.session.SessionToken;
import com.trc.web.validation.CouponDetailValidator;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Controller
@RequestMapping("/coupons")
@SessionAttributes("couponDetailList")
public class CouponController {
  @Autowired
  private CouponManager couponManager;
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;
  @Autowired
  private CouponValidator couponValidator;
  @Autowired
  private CouponDetailValidator couponDetailValidator;

  protected void encodeAccountNums(List<AccountDetail> accountDetailList) {
    for (AccountDetail accountDetail : accountDetailList) {
      accountDetail.setEncodedAccountNum(SessionEncrypter.encryptId(accountDetail.getAccount().getAccountno()));
    }
  }

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

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showRedeemCoupon() {
    ResultModel model = new ResultModel("coupon/addCoupon");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    List<AccountDetail> accountList = overview.getAccountDetails();

    boolean hasActiveDevice = false;
    if (accountList != null) {
      for (AccountDetail ad : accountList) {
        if (ad.getDeviceInfo().getStatusId() == 2) {
          hasActiveDevice = true;
        }
      }
    }
    if (hasActiveDevice) {

      CouponRequest couponRequest = new CouponRequest();
      couponRequest.setCoupon(new Coupon());
      couponRequest.setSessionToken(SessionManager.createToken(SessionRequest.COUPON, "coupon request for user " + user.getUserId()));

      model.addObject("couponRequest", couponRequest);
      model.addObject("accountList", accountList);
      return model.getSuccess();
    } else {
      return new ResultModel("coupon/noDevices").getSuccess();
    }
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
      return model.getAccessException();
    }

    SessionToken token = SessionManager.fetchToken(couponRequest.getSessionToken().getRequest());
    if (token != null && token.getId().equals(couponRequest.getSessionToken().getId())) {
      try {
        couponRequest.setCoupon(couponManager.getCouponByCode(couponRequest.getCoupon().getCouponCode()));
        couponValidator.validate(couponRequest, accountNumber, result);
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
          model.addObject("couponRequest", couponRequest);
          return model.getSuccess();
        }
      } catch (CouponManagementException e) {
        return model.getAccessException();
      }
    } else {
      return model.getAccessException();
    }

  }

  /**
   * This method is used to show the form to create a coupon
   * 
   * @return ModelAndView
   */
  @RequestMapping(value = "/createCoupon", method = RequestMethod.GET)
  public ModelAndView createCoupon() {
    ResultModel model = new ResultModel("coupon/createCoupon");
    model.addObject("coupon", new Coupon());
    List<CouponDetail> couponDetailList = null;
    try {
      couponDetailList = couponManager.getAllCouponDetails();
    } catch (CouponManagementException ce) {
      ce.printStackTrace();
    }
    model.addObject("couponDetailList", couponDetailList);

    return model.getSuccess();
  }

  /**
   * This method is used to create a coupon
   * 
   * @return ModelAndView
   */
  @RequestMapping(value = "/createCoupon", method = RequestMethod.POST)
  public ModelAndView postCreateCoupon(HttpServletRequest request, @ModelAttribute("coupon") Coupon coupon, BindingResult result, SessionStatus status) {
    ResultModel model = new ResultModel("coupon/createCouponSuccess", "coupon/createCoupon");
    couponValidator.validateForCreateCoupon(coupon, result);
    if (result.hasErrors()) {
      return model.getError();
    }
    try {
      couponManager.insertCoupon(coupon);
      model.addObject("coupon", coupon);
      status.setComplete();
    } catch (CouponManagementException e) {
      return model.getAccessException();
    }
    return model.getSuccess();
  }

  /*
   * @RequestMapping(value = "/createNewCoupon", method = RequestMethod.GET)
   * public ModelAndView createNewCoupon() { ResultModel model = new
   * ResultModel("coupon/createNewCoupon"); model.addObject("coupon", new
   * Coupon()); List<CouponDetail> couponDetailList = null; try {
   * couponDetailList = couponManager.getAllCouponDetails(); }
   * catch(CouponManagementException ce){ ce.printStackTrace(); }
   * model.addObject("couponDetailList", couponDetailList); return
   * model.getSuccess(); }
   * 
   * @RequestMapping(value = "/createNewCoupon", method = RequestMethod.POST)
   * public ModelAndView postCreateNewCoupon(HttpServletRequest request,
   * 
   * @ModelAttribute("coupon") Coupon coupon, BindingResult result) {
   * DevLogger.log("from submission caught, input coupon: "+
   * coupon.toFormattedString()); ResultModel model = new
   * ResultModel("coupon/createCouponSuccess", "coupon/createNewCoupon"); try {
   * //couponValidator.validate(coupon, accountNumber, result); int
   * couponDetailId = couponManager.insertCoupon(coupon);
   * model.addObject("coupon", coupon); } catch (CouponManagementException e) {
   * e.printStackTrace(); return model.getAccessException(); } return
   * model.getSuccess(); }
   */

  /**
   * This method is used to show the form to create a coupon detail
   * 
   * @return ModelAndView
   */
  @RequestMapping(value = "/createCouponDetail", method = RequestMethod.GET)
  public ModelAndView createCouponDetail() {
    ResultModel model = new ResultModel("coupon/createCouponDetail");
    model.addObject("couponDetail", new CouponDetail());
    List<Contract> contractList = null;
    List<CouponDetail> couponDetailList = null;
    List<CouponDetailType> couponDetailTypeList = null;
    List<String> unitList = new ArrayList<String>();
    Set<String> set = new HashSet<String>();
    try {
      contractList = couponManager.getAllContracts();
      couponDetailList = couponManager.getAllCouponDetails();
      couponDetailTypeList = couponManager.getCouponDetailTypes();
    } catch (CouponManagementException ce) {
      ce.printStackTrace();
      return model.getAccessException();
    }
    for (CouponDetail cd : couponDetailList) {
      if (cd.getDurationUnit() != null && cd.getDurationUnit().length() > 0)
        set.add(cd.getDurationUnit());
    }
    DevLogger.log("from DB, contractList size: " + (contractList == null ? 0 : contractList.size()));
    DevLogger.log("couponDetailTypeList size: " + (couponDetailTypeList == null ? 0 : couponDetailTypeList.size()));
    unitList.addAll(set);
    Collections.sort(unitList);
    model.addObject("contractList", contractList);
    model.addObject("unitList", unitList);
    model.addObject("couponDetailTypeList", couponDetailTypeList);
    return model.getSuccess();
  }

  /**
   * This method is used to to create a coupon detail
   * 
   * @return ModelAndView
   */
  @RequestMapping(value = "/createCouponDetail", method = RequestMethod.POST)
  public ModelAndView postCreateCouponDetail(HttpServletRequest request, @ModelAttribute("couponDetail") CouponDetail couponDetail, BindingResult result) {
    ResultModel model = new ResultModel("coupon/createCouponDetailSuccess", "coupon/createCouponDetail");
    couponDetailValidator.validate(couponDetail, result);
    if (result.hasErrors()) {
      model.addObject("couponDetail", couponDetail);
      return model.getError();
    } else {
      try {
        int couponDetailId = couponManager.insertCouponDetail(couponDetail);
        couponDetail.setCouponDetailId(couponDetailId);
        couponManager.insertCouponStackable(couponDetail);
      } catch (CouponManagementException e) {
        return model.getAccessException();
      }
      model.addObject("couponDetail", couponDetail);
      return model.getSuccess();
    }
  }

  @RequestMapping(value = "/showAllCoupons", method = RequestMethod.GET)
  public ModelAndView showAllCoupons() {
    ResultModel model = new ResultModel("coupon/showAllCoupons", "coupon/showAllCoupons");
    List<Coupon> couponList = null;
    try {
      couponList = couponManager.getAllCoupons();
    } catch (CouponManagementException ce) {
      ce.printStackTrace();
    }
    model.addObject("couponList", couponList);
    return model.getSuccess();
  }

  @RequestMapping(value = "/getCouponByCode", method = RequestMethod.GET)
  public ModelAndView getCouponByCode() {
    ResultModel model = new ResultModel("coupon/getCouponByCode");
    model.addObject("coupon", new Coupon());
    List<Coupon> couponList = null;
    // fetch all existing coupons and provide coupon codes for user to select
    // from
    try {
      couponList = couponManager.getAllCoupons();
    } catch (CouponManagementException ce) {
      ce.printStackTrace();
    }
    model.addObject("couponList", couponList);
    return model.getSuccess();
  }

  @RequestMapping(value = "/getCouponByCode", method = RequestMethod.POST)
  public ModelAndView postGetCouponByCode(@ModelAttribute("coupon") Coupon coupon, BindingResult result) {
    ResultModel model = new ResultModel("coupon/showAllCoupons", "coupon/getCouponByCode");
    couponValidator.checkCouponCode(coupon == null ? "" : coupon.getCouponCode(), result);
    if (result.hasErrors()) {
      return model.getError();
    }
    List<Coupon> couponList = new ArrayList<Coupon>();
    Coupon retrievedCoupon = null;
    try {
      retrievedCoupon = couponManager.getCouponByCode(coupon.getCouponCode());
    } catch (CouponManagementException e) {
      return model.getAccessException();
    }
    couponList.add(retrievedCoupon);
    model.addObject("couponList", couponList);
    return model.getSuccess();
  }

  @RequestMapping(value = "/createCouponDetailType", method = RequestMethod.GET)
  public ModelAndView createCouponDetailType() {
    ResultModel model = new ResultModel("coupon/createCouponDetailType");
    model.addObject("couponDetailType", new CouponDetailType());
    return model.getSuccess();
  }

  @RequestMapping(value = "/createCouponDetailType", method = RequestMethod.POST)
  public ModelAndView postcreateCouponDetailType(HttpServletRequest request, @ModelAttribute("couponDetailType") CouponDetailType couponDetailType,
      BindingResult result) {

    DevLogger.log("form submission caught, input couponDetailType: " + couponDetailType.toString());
    ResultModel model = new ResultModel("coupon/createCouponDetailTypeSuccess", "coupon/createCouponDetailType");
    try {
      // couponValidator.validate(coupon, accountNumber, result);
      int couponDetailTypeId = couponManager.insertCouponDetailType(couponDetailType);
    } catch (CouponManagementException e) {
      return model.getAccessException();
    }
    // couponValidator.validateForCreateCoupon(coupon, result);
    model.addObject("couponDetailType", couponDetailType);
    return model.getSuccess();
  }

  @RequestMapping(value = "/createCouponContract", method = RequestMethod.GET)
  public ModelAndView createCouponContract() {
    ResultModel model = new ResultModel("coupon/createCouponContract");
    model.addObject("contract", new Contract());
    return model.getSuccess();
  }

  @RequestMapping(value = "/createCouponContract", method = RequestMethod.POST)
  public ModelAndView postCreateCouponContract(HttpServletRequest request, @ModelAttribute("contract") Contract contract, BindingResult result) {
    ResultModel model = new ResultModel("coupon/createCouponContractSuccess", "coupon/createCouponContract");
    try {
      int contractType = couponManager.insertCouponContract(contract);
      model.addObject("couponContract", contract);
    } catch (CouponManagementException e) {
      return model.getAccessException();
    }
    return model.getSuccess();
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("MM/dd/yy"), true);
    binder.registerCustomEditor(Date.class, editor);
  }
}