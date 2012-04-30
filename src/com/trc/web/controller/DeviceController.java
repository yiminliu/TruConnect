package com.trc.web.controller;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.CouponManagementException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.management.DeviceSwapException;
import com.trc.manager.impl.AccountManager;
import com.trc.manager.impl.CouponManager;
import com.trc.manager.impl.DeviceManager;
import com.trc.manager.impl.UserManager;
import com.trc.payment.coupon.UserCoupon;
import com.trc.security.encryption.SessionEncrypter;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.util.logger.DevLogger;
import com.trc.web.model.ResultModel;
import com.trc.web.session.SessionManager;
import com.trc.web.session.SessionRequest;
import com.trc.web.validation.DeviceValidator;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

@Controller
@RequestMapping("/devices")
public class DeviceController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;
  @Autowired
  private DeviceManager deviceManager;
  @Autowired
  private CouponManager couponManager;
  @Autowired
  private DeviceValidator deviceValidator;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showDevices() {
    ResultModel model = new ResultModel("devices/devices");
    User user = userManager.getCurrentUser();
    try {
      List<AccountDetail> devices = accountManager.getAccountDetailList(user);
      encodeDeviceIds(devices);
      model.addObject("devices", devices);
      return model.getSuccess();
    } catch (AccountManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/rename/{encodedDeviceId}", method = RequestMethod.GET)
  public ModelAndView renameDevice(@PathVariable String encodedDeviceId) {
    ResultModel model = new ResultModel("devices/rename");
    User user = userManager.getCurrentUser();
    try {
      Device deviceToRename = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
      SessionManager.set(SessionRequest.DEVICE_RENAME, deviceToRename);
      model.addObject("device", deviceToRename);
      return model.getSuccess();
    } catch (DeviceManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/rename/{encodedDeviceId}", method = RequestMethod.POST)
  public ModelAndView postRenameDevice(@PathVariable String encodedDeviceId, @ModelAttribute Device device, Errors errors) {
    ResultModel model = new ResultModel("devices/renameSuccess", "devices/rename");
    User user = userManager.getCurrentUser();
    deviceValidator.validateFields(device, errors);
    if (errors.hasErrors()) {
      model.addObject("device", device);
      return model.getError();
    } else {
      String newDeviceLabel = device.getLabel();
      try {
        device = (Device) SessionManager.get(SessionRequest.DEVICE_RENAME);
        if (device == null) {
          device = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
        }
        model.addObject("oldDeviceLabel", device.getLabel());
        device.setLabel(newDeviceLabel);
        deviceManager.updateDeviceInfo(user, device);
        model.addObject("newDeviceLabel", device.getLabel());
        return model.getSuccess();
      } catch (DeviceManagementException e) {
        errors.reject("device.update.label.error", null, "There was an error renaming your device");
        return model.getError();
      }
    }
  }

  @RequestMapping(value = "/swap/{encodedDeviceId}", method = RequestMethod.GET)
  public ModelAndView showSwapDevice(@PathVariable String encodedDeviceId) {
    ResultModel model = new ResultModel("devices/swapEsn");
    User user = userManager.getCurrentUser();
    try {
      Device deviceFrom = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
      Device deviceTo = TruConnectUtil.clone(deviceFrom);
      deviceTo.setValue(null);
      SessionManager.set(SessionRequest.DEVICE_SWAP, deviceFrom);
      model.addObject("device", deviceTo);
      return model.getSuccess();
    } catch (DeviceManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/swap/{encodedDeviceId}", method = RequestMethod.POST)
  public ModelAndView postSwapDevice(@PathVariable String encodedDeviceId, @ModelAttribute Device device, Errors errors) {
    ResultModel model = new ResultModel("devices/swapEsnSuccess", "devices/swapEsn");
    User user = userManager.getCurrentUser();
    deviceValidator.validate(device, errors);
    if (errors.hasErrors()) {
      model.addObject("device", device);
      return model.getError();
    } else {
      int deviceId = SessionEncrypter.decryptId(encodedDeviceId);
      String newEsn = device.getValue();
      String newDeviceLabel = device.getLabel();
      try {
        Device oldDeviceInfo = (Device) SessionManager.get(SessionRequest.DEVICE_SWAP);
        Device newDeviceInfo = TruConnectUtil.clone(oldDeviceInfo);
        if (oldDeviceInfo == null) {
          oldDeviceInfo = deviceManager.getDeviceInfo(user, deviceId);
        }
        if (oldDeviceInfo.getStatusId() != 2) {
          throw new DeviceSwapException("Device swapped from is not active");
        }
        if (newDeviceInfo == null) {
          newDeviceInfo = deviceManager.getDeviceInfo(user, deviceId);
        }
        newDeviceInfo.setValue(newEsn);
        newDeviceInfo.setLabel(newDeviceLabel);
        deviceManager.swapDevice(user, oldDeviceInfo, newDeviceInfo);
        return model.getSuccess();
      } catch (DeviceSwapException e) {
        DevLogger.log("DeviceSwapException caught: {}", e.getMessage(), e);
        errors.rejectValue("value", "device.swap.error.inactive");
        model.addObject("deviceInfo", device);
        return model.getError();
      } catch (DeviceManagementException e) {
        DevLogger.log("DeviceManagementException caught: {}", e.getMessage());
        if (e.getMessage().contains("Device is currently assigned")) {
          DevLogger.log("rejecting value with code device.swap.error.active");
          errors.rejectValue("value", "device.swap.error.active");
        } else if (e.getMessage().contains("Device is currently in reserve")) {
          DevLogger.log("rejecting value with code device.swap.error.active");
          errors.rejectValue("value", "device.swap.error.active");
        } else {
          DevLogger.log("rejecting value with code device.swap.error");
          errors.rejectValue("value", "device.swap.error");
        }
        model.addObject("deviceInfo", device);
        return model.getError();
      }
    }
  }

  @RequestMapping(value = "/deactivate/{encodedDeviceId}", method = RequestMethod.GET)
  public ModelAndView showDeactivateDevice(@PathVariable String encodedDeviceId) {
    ResultModel model = new ResultModel("devices/deactivatePrompt");
    User user = userManager.getCurrentUser();
    try {
      Device device = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
      Account account = accountManager.getAccount(device.getAccountNo());
      XMLGregorianCalendar accessFeeDate = accountManager.getLastAccessFeeDate(user, account);
      SessionManager.set(SessionRequest.DEVICE_DEACTIVATE, device);
      SessionManager.set(SessionRequest.DEVICE_ACCOUNT, account);
      SessionManager.set(SessionRequest.DEVICE_ACCESSFEEDATE, accessFeeDate);
      model.addObject("accessFeeDate", accessFeeDate);
      model.addObject("account", account);
      model.addObject("device", device);
      return model.getSuccess();
    } catch (DeviceManagementException e) {
      return model.getAccessException();
    } catch (AccountManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/deactivate/{encodedDeviceId}", method = RequestMethod.POST)
  public ModelAndView postDeactivateDevice(@PathVariable String encodedDeviceId, @ModelAttribute Device device, Errors errors) {
    ResultModel model = new ResultModel("devices/deactivateSuccess", "devices/deactivatePrompt");
    User user = userManager.getCurrentUser();
    Device deviceLookup = (Device) SessionManager.get(SessionRequest.DEVICE_DEACTIVATE);
    Account account = (Account) SessionManager.get(SessionRequest.DEVICE_ACCOUNT);
    XMLGregorianCalendar accessFeeDate = (XMLGregorianCalendar) SessionManager.get(SessionRequest.DEVICE_ACCESSFEEDATE);
    try {
      if (deviceLookup == null) {
        deviceLookup = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
      }
      if (account == null) {
        account = accountManager.getAccount(deviceLookup.getAccountNo());
      }
      if (accessFeeDate == null) {
        accessFeeDate = accountManager.getLastAccessFeeDate(user, account);
      }
    } catch (DeviceManagementException e) {
      return model.getAccessException();
    } catch (AccountManagementException e) {
      return model.getAccessException();
    }

    try {
      NetworkInfo networkInfo = deviceManager.getNetworkInfo(deviceLookup.getValue(), null);
      if (!deviceManager.compareEsn(deviceLookup, networkInfo)) {
        model.addObject("accessFeeDate", accessFeeDate);
        model.addObject("account", account);
        model.addObject("deviceInfo", deviceLookup);
        errors.rejectValue("value", "device.deactivate.error");
        return model.getError();
      } else {
        ServiceInstance serviceInstance = TruConnectUtil.toServiceInstance(networkInfo);

        // cancel all coupons associated with account
        List<UserCoupon> coupons = couponManager.getUserCoupons(user.getUserId());
        DevLogger.log("found coupons {}", coupons);
        if (coupons != null && !coupons.isEmpty()) {
          for (UserCoupon uc : coupons) {
            if (uc.getId().getAccountNumber() == account.getAccountno()) {
              if (uc.getId().getCoupon().isContract()) {
                couponManager.cancelCoupon(uc.getId().getCoupon(), user, account, serviceInstance);
              }
            }
          }
        }

        deviceManager.disconnectService(serviceInstance);
        model.addObject("label", deviceLookup.getLabel());
        return model.getSuccess();
      }
    } catch (DeviceManagementException e) {
      model.addObject("accessFeeDate", accessFeeDate);
      model.addObject("account", account);
      model.addObject("device", deviceLookup);
      errors.rejectValue("value", "device.deactivate.error");
      return model.getError();
    } catch (CouponManagementException e) {
      model.addObject("accessFeeDate", accessFeeDate);
      model.addObject("account", account);
      model.addObject("device", deviceLookup);
      errors.rejectValue("value", "device.deactivate.error");
      return model.getError();
    }
  }

  @RequestMapping(value = "/reinstall/{encodedDeviceId}", method = RequestMethod.GET)
  public ModelAndView showReinstallDevice(@PathVariable String encodedDeviceId) {
    ResultModel model = new ResultModel("devices/reinstallPrompt");
    User user = userManager.getCurrentUser();
    try {
      Device deviceToReactivate = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
      SessionManager.set(SessionRequest.DEVICE_REACTIVATE, deviceToReactivate);
      model.addObject("deviceInfo", deviceToReactivate);
      return model.getSuccess();
    } catch (DeviceManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/reinstall/{encodedDeviceId}", method = RequestMethod.POST)
  public ModelAndView postReinstallDevice(@PathVariable String encodedDeviceId, @ModelAttribute Device deviceInfo, Errors errors) {
    ResultModel model = new ResultModel("redirect:/devices", "devices/reinstallPrompt");
    User user = userManager.getCurrentUser();
    try {
      Device deviceToReactivate = (Device) SessionManager.get(SessionRequest.DEVICE_REACTIVATE);
      if (deviceToReactivate == null) {
        deviceToReactivate = deviceManager.getDeviceInfo(user, SessionEncrypter.decryptId(encodedDeviceId));
      }
      deviceManager.reinstallCustomerDevice(user, deviceToReactivate);
      return model.getSuccess();
    } catch (DeviceManagementException e) {
      errors.rejectValue("value", "device.reinstall.error");
      return model.getError();
    }
  }

  @RequestMapping(value = "/topUp/{encodedDeviceId}", method = RequestMethod.GET)
  public ModelAndView showChangeTopUp(@PathVariable String encodedDeviceId) {
    ResultModel model = new ResultModel("devices/changeTopUp");
    User user = userManager.getCurrentUser();
    try {
      int deviceId = SessionEncrypter.decryptId(encodedDeviceId);
      AccountDetail accountDetail = accountManager.getAccountDetail(user, deviceId);
      // SessionManager.set(SessionKey.DEVICE_ACCOUNTDETAIL, accountDetail);
      model.addObject("accountDetail", accountDetail);
      return model.getSuccess();
    } catch (AccountManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/topUp/{encodedDeviceId}", method = RequestMethod.POST)
  public ModelAndView postChangeTopUp(@PathVariable String encodedDeviceId, @ModelAttribute AccountDetail accountDetail, Errors errors) {
    ResultModel model = new ResultModel("devices/changeTopUpSuccess", "devices/changeTopUp");
    User user = userManager.getCurrentUser();
    int deviceId = SessionEncrypter.decryptId(encodedDeviceId);
    String newTopUp = accountDetail.getTopUp();
    accountDetail = (AccountDetail) SessionManager.get(SessionRequest.DEVICE_ACCOUNTDETAIL);
    try {
      if (accountDetail == null) {
        accountDetail = accountManager.getAccountDetail(user, deviceId);
      }
      accountDetail.setTopUp(newTopUp);
      accountManager.setTopUp(user, new Double(newTopUp), accountDetail.getAccount());
      model.addObject("accountDetail", accountDetail);
      return model.getSuccess();
    } catch (AccountManagementException e) {
      model.addObject("accountDetail", accountDetail);
      errors.rejectValue("topUp", "account.topUp.change.error");
      return model.getError();
    }
  }

  private void encodeDeviceIds(List<AccountDetail> accountDetailList) {
    for (AccountDetail accountDetail : accountDetailList) {
      accountDetail.setEncodedDeviceId(SessionEncrypter.encryptId(accountDetail.getDeviceInfo().getId()));
    }
  }

}