package com.trc.manager;

import java.util.Collection;
import java.util.List;

import com.trc.exception.management.DeviceManagementException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

public interface DeviceManagerModel {

  public NetworkInfo reserveMdn() throws DeviceManagementException;

  public NetworkInfo getNetworkInfo(String esn, String msid) throws DeviceManagementException;

  public Collection<Device> getDeviceInfoList(User user) throws DeviceManagementException;

  public Device addDeviceInfo(Device deviceInfo, Account account, User user) throws DeviceManagementException;

  public List<Device> removeDeviceInfo(Device deviceInfo, Account account, User user) throws DeviceManagementException;

  public void updateDeviceInfo(User user, Device deviceInfo) throws DeviceManagementException;

  public NetworkInfo swapDevice(User user, Device oldDeviceInfo, Device newDeviceInfo) throws DeviceManagementException;

  public NetworkInfo activateService(NetworkInfo networkInfo, User user) throws DeviceManagementException;

  public void suspendService(int userId, int accountNo, int deviceId) throws DeviceManagementException;

  public void restoreService(int userId, int accountNo, int deviceId) throws DeviceManagementException;

  public Account createServiceInstance(Account account, NetworkInfo networkInfo) throws DeviceManagementException;

  public void disconnectService(ServiceInstance serviceInstance) throws DeviceManagementException;

  public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceManagementException;

  public void disconnectFromKenan(Account account, ServiceInstance serviceInstance) throws DeviceManagementException;

  public NetworkInfo reinstallCustomerDevice(User user, Device deviceInfo) throws DeviceManagementException;
}