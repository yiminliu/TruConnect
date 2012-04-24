package com.trc.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.management.CouponManagementException;
import com.trc.exception.service.CouponServiceException;
import com.trc.manager.CouponManagerModel;
import com.trc.payment.coupon.Coupon;
import com.trc.payment.coupon.CouponDetail;
import com.trc.payment.coupon.CouponDetailType;
import com.trc.payment.coupon.UserCoupon;
import com.trc.payment.coupon.contract.Contract;
import com.trc.service.impl.CouponService;
import com.trc.user.User;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.validation.CouponValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Component
public class CouponManager implements CouponManagerModel {
  @Autowired
  private CouponService couponService;
  @Autowired
  private CouponValidator couponValidator;

  @Loggable(value = LogLevel.TRACE)
  public List<UserCoupon> getUserCoupons(int userId) throws CouponManagementException {
    try {
      return couponService.getUserCoupons(userId);
    } catch (CouponServiceException e) {
      throw new CouponManagementException("Error getting UserCoupon for user " + userId + ": " + e.getMessage());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public UserCoupon getUserCoupon(UserCoupon userCoupon) throws CouponManagementException {
    try {
      return couponService.getUserCoupon(userCoupon);
    } catch (CouponServiceException e) {
      throw new CouponManagementException("Error getting UserCoupon for user " + userCoupon.getId().getUserId() + ": " + e.getMessage());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public int applyCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance) throws CouponManagementException {
    if (!couponValidator.isAtAccountLimit(coupon, user, account)) {
      try {
        if (coupon.getCouponDetail().getContract().getContractType() == -1) {
          return couponService.applyCouponPayment(coupon, user, account, new Date());
        } else {
          return couponService.applyCoupon(user, coupon, account, serviceInstance);
        }
      } catch (CouponServiceException e) {
        throw new CouponManagementException(e.getMessage(), e.getCause());
      }
    } else {
      throw new CouponManagementException("Coupon " + coupon.getCouponId() + " has already been applied to it's limit by user " + user.getUserId());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void cancelCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance) throws CouponManagementException {
    try {
      couponService.cancelCoupon(user, coupon, account, serviceInstance);
    } catch (CouponServiceException e) {
      // TODO should attempt to cancel again or queue for cancellation
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  /* *****************************************************************
   * Coupon Management
   * *****************************************************************
   */

  @Loggable(value = LogLevel.TRACE)
  public int insertCoupon(Coupon coupon) throws CouponManagementException {
    try {
      return couponService.insertCoupon(coupon);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void deleteCoupon(Coupon coupon) throws CouponManagementException {
    try {
      couponService.deleteCoupon(coupon);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void updateCoupon(Coupon coupon) throws CouponManagementException {
    try {
      couponService.updateCoupon(coupon);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public Coupon getCoupon(int couponId) throws CouponManagementException {
    try {
      return couponService.getCoupon(couponId);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<Coupon> getAllCoupons() throws CouponManagementException {
    try {
      return couponService.getAllCoupons();
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public Coupon getCouponByCode(String couponCode) throws CouponManagementException {
    try {
      Coupon coupon = couponService.getCouponByCode(couponCode);
      return coupon;
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  /* *****************************************************************
   * CouponDetail Management
   * *****************************************************************
   */

  @Loggable(value = LogLevel.TRACE)
  public int insertCouponDetail(CouponDetail couponDetail) throws CouponManagementException {
    try {
      return couponService.insertCouponDetail(couponDetail);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void deleteCouponDetail(CouponDetail couponDetail) throws CouponManagementException {
    try {
      couponService.deleteCouponDetail(couponDetail);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void updateCouponDetail(CouponDetail couponDetail) throws CouponManagementException {
    try {
      couponService.updateCouponDetail(couponDetail);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public CouponDetail getCouponDetail(int couponDetailId) throws CouponManagementException {
    try {
      return couponService.getCouponDetail(couponDetailId);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<CouponDetail> getAllCouponDetails() throws CouponManagementException {
    try {
      return couponService.getAllCouponDetails();
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  /* *****************************************************************
   * CouponDetailType Management
   * *****************************************************************
   */
  @Loggable(value = LogLevel.TRACE)
  public List<CouponDetailType> getCouponDetailTypes() throws CouponManagementException {
    try {
      return couponService.getAllCouponDetailTypes();
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public int insertCouponDetailType(CouponDetailType couponDetailType) throws CouponManagementException {
    try {
      return couponService.insertCouponDetailType(couponDetailType);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  /* *****************************************************************
   * CouponContract Management
   * *****************************************************************
   */
  @Loggable(value = LogLevel.TRACE)
  public List<Contract> getAllContracts() throws CouponManagementException {
    try {
      return couponService.getAllContracts();
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public int insertCouponContract(Contract contract) throws CouponManagementException {
    try {
      return couponService.insertCouponContract(contract);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

  /* *****************************************************************
   * CouponStackable Management
   * *****************************************************************
   */
  @Loggable(value = LogLevel.TRACE)
  public void insertCouponStackable(CouponDetail couponDetail) throws CouponManagementException {
    try {
      couponService.insertCouponStackable(couponDetail);
    } catch (CouponServiceException e) {
      throw new CouponManagementException(e.getMessage(), e.getCause());
    }
  }

}