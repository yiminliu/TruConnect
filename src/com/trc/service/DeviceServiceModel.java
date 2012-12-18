package com.trc.service;

import java.util.Collection;
import java.util.List;

import com.trc.exception.service.DeviceServiceException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

public interface DeviceServiceModel {

  public NetworkInfo getNetworkInfo(String esn, String msid) throws DeviceServiceException;

  public void updateDeviceInfo(User user, Device deviceInfo) throws DeviceServiceException;

  public Collection<Device> getDeviceInfoList(User user) throws DeviceServiceException;

  public NetworkInfo swapDevice(User user, NetworkInfo oldNetworkInfo, Device deviceInfo)
      throws DeviceServiceException;

  public NetworkInfo reserveMdn() throws DeviceServiceException;

  public Device addDeviceInfo(User user, Device deviceInfo) throws DeviceServiceException;

  public List<Device> deleteDeviceInfo(User user, Device deviceInfo) throws DeviceServiceException;

  public void suspendService(int userId, int accountNo, int deviceId) throws DeviceServiceException;

  public void restoreService(int userId, int accountNo, int deviceId) throws DeviceServiceException;

  public NetworkInfo activateService(User user, NetworkInfo networkInfo) throws DeviceServiceException;

  public Account createServiceInstance(Account account, ServiceInstance serviceInstance) throws DeviceServiceException;

  public void disconnectService(ServiceInstance serviceInstance) throws DeviceServiceException;

  public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceServiceException;

  public void disconnectFromKenan(Account account, ServiceInstance serviceInstance) throws DeviceServiceException;

  public NetworkInfo reinstallCustomerDevice(User user, Device deviceInfo) throws DeviceServiceException;
}
