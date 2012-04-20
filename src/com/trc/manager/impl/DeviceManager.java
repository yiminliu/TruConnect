package com.trc.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.service.DeviceServiceException;
import com.trc.manager.DeviceManagerModel;
import com.trc.service.impl.DeviceService;
import com.trc.user.User;
import com.trc.util.cache.CacheKey;
import com.trc.util.cache.CacheManager;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

@Service
public class DeviceManager implements DeviceManagerModel {
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private UserManager userManager;

  @Loggable(value = LogLevel.TRACE)
  public void setDefaultDeviceLabel(Device deviceInfo, String firstName) {
    deviceInfo.setLabel(firstName + "'s TruConnect Device");
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo reserveMdn() throws DeviceManagementException {
    try {
      return deviceService.reserveMdn();
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo getNetworkInfo(String esn, String msid) throws DeviceManagementException {
    try {
      return deviceService.getNetworkInfo(esn, msid);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Device> getDeviceInfoList(User user) throws DeviceManagementException {
    List<Device> deviceInfoList = getDevicesFromCache(user);
    if (deviceInfoList != null) {
      return deviceInfoList;
    } else {
      try {
        deviceInfoList = deviceService.getDeviceInfoList(user);
        saveDevicesToCache(user, deviceInfoList);
        return deviceInfoList;
      } catch (DeviceServiceException e) {
        throw new DeviceManagementException(e.getMessage(), e.getCause());
      }
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public Device getDeviceInfo(User user, int deviceId) throws DeviceManagementException {
    try {
      List<Device> deviceInfoList = getDeviceInfoList(user);
      for (Device deviceInfo : deviceInfoList) {
        if (deviceInfo.getId() == deviceId)
          return deviceInfo;
      }
      return null;
    } catch (DeviceManagementException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Device addDeviceInfo(Device deviceInfo, Account account, User user) throws DeviceManagementException {
    try {
      deviceInfo.setId(0);
      deviceInfo.setCustId(user.getUserId());
      deviceInfo.setAccountNo(account.getAccountno());
      clearDevicesFromCache(user);
      return deviceService.addDeviceInfo(user, deviceInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Device> removeDeviceInfo(Device deviceInfo, Account account, User user) throws DeviceManagementException {
    try {
      clearDevicesFromCache(user);
      return deviceService.deleteDeviceInfo(user, deviceInfo);
    } catch (DeviceServiceException e) {
      e.printStackTrace();
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void updateDeviceInfo(User user, Device deviceInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache(user);
      deviceService.updateDeviceInfo(user, deviceInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo swapDevice(User user, Device oldDeviceInfo, Device newDeviceInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache(user);
      NetworkInfo oldNetworkInfo = deviceService.getNetworkInfo(oldDeviceInfo.getValue(), null);
      NetworkInfo newNetworkInfo = deviceService.swapDevice(user, oldNetworkInfo, newDeviceInfo);
      return newNetworkInfo;
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo activateService(NetworkInfo networkInfo, User user) throws DeviceManagementException {
    try {
      return deviceService.activateService(user, networkInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void suspendService(int userId, int accountNo, int deviceId) throws DeviceManagementException {
    try {
      clearDevicesFromCache(userManager.getCurrentUser());
      deviceService.suspendService(userId, accountNo, deviceId);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void restoreService(int userId, int accountNo, int deviceId) throws DeviceManagementException {
    try {
      clearDevicesFromCache(userManager.getCurrentUser());
      deviceService.restoreService(userId, accountNo, deviceId);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Account createServiceInstance(Account account, NetworkInfo networkInfo) throws DeviceManagementException {
    try {
      ServiceInstance serviceInstance = new ServiceInstance();
      serviceInstance.setExternalId(networkInfo.getMdn());
      return deviceService.createServiceInstance(account, serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void disconnectService(ServiceInstance serviceInstance) throws DeviceManagementException {
    try {
      clearDevicesFromCache(userManager.getCurrentUser());
      deviceService.disconnectService(serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void disconnectService(Account account) throws DeviceManagementException {
    try {
      disconnectService(account.getServiceinstancelist().get(0));
    } catch (DeviceManagementException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache(userManager.getCurrentUser());
      deviceService.disconnectFromNetwork(networkInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void disconnectFromKenan(Account account, ServiceInstance serviceInstance) throws DeviceManagementException {
    try {
      deviceService.disconnectFromKenan(account, serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo reinstallCustomerDevice(User user, Device deviceInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache(user);
      return deviceService.reinstallCustomerDevice(user, deviceInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public boolean isDeviceAvailable(String esn) {
    try {
      NetworkInfo networkInfo = deviceService.getNetworkInfo(esn, null);
      DevLogger.log("isDeviceAvailable: " + esn + " received " + networkInfo);
      if (networkInfo != null && compareEsn(networkInfo, esn) && !isEsnInUse(networkInfo)) {
        return true;
      } else {
        return false;
      }
    } catch (DeviceServiceException e) {
      return false;
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void bindEsn(NetworkInfo networkInfo, Device deviceInfo) {
    String esn = deviceInfo.getValue();
    if (isDec(esn)) {
      networkInfo.setEsnmeiddec(esn);
    } else if (isHex(esn)) {
      networkInfo.setEsnmeidhex(esn);
    }
  }

  public boolean compareEsn(Device deviceInfo, NetworkInfo networkInfo) {
    String deviceEsn = deviceInfo.getValue();
    return compareEsn(networkInfo, deviceEsn);
  }

  private boolean compareEsn(NetworkInfo networkInfo, String esn) {
    DevLogger.log("comparEsn: " + networkInfo.getEsnmeiddec() + " " + networkInfo.getEsnmeidhex() + " " + esn);
    return networkInfo != null && (esn.equals(networkInfo.getEsnmeiddec()) || esn.equals(networkInfo.getEsnmeidhex()));
  }

  // TODO possibly check for status R as well
  private boolean isEsnInUse(NetworkInfo networkInfo) {
    DevLogger.log("isEsnInUse: " + networkInfo.getStatus());
    return networkInfo.getStatus() != null
        && (networkInfo.getStatus().equals("A") || networkInfo.getStatus().equals("S") || networkInfo.getStatus().equals("H"));
  }

  private static boolean isDec(String esn) {
    return esn.matches("\\d+") && (esn.length() == 11 || esn.length() == 18);
  }

  private static boolean isHex(String esn) {
    return !esn.matches("\\d+") && (esn.length() == 8 || esn.length() == 14);
  }

  /* *************************************************************
   * CacheManger helper functions
   * *************************************************************
   */

  @SuppressWarnings("unchecked")
  private List<Device> getDevicesFromCache(User user) {
    return (List<Device>) CacheManager.get(user, CacheKey.DEVICES);
  }

  private void clearDevicesFromCache(User user) {
    CacheManager.clearCache(user, CacheKey.DEVICES);
  }

  private void saveDevicesToCache(User user, List<Device> deviceInfoList) {
    CacheManager.set(user, CacheKey.DEVICES, deviceInfoList);
  }

}