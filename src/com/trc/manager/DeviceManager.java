package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.management.DeviceActivationException;
import com.trc.exception.management.DeviceDisconnectException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.management.DeviceReservationException;
import com.trc.exception.management.DeviceServiceCreationException;
import com.trc.exception.service.DeviceServiceException;
import com.trc.service.DeviceService;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.session.cache.CacheKey;
import com.trc.web.session.cache.CacheManager;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

@Service
public class DeviceManager implements DeviceManagerModel {
	@Autowired
	private DeviceService deviceService;

	@Loggable(value = LogLevel.TRACE)
	public void setDefaultDeviceLabel(Device deviceInfo, String firstName) {
		deviceInfo.setLabel(firstName + "'s Device");
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public NetworkInfo reserveMdn() throws DeviceReservationException {
		try {
			return deviceService.reserveMdn();
		} catch (DeviceServiceException e) {
			throw new DeviceReservationException(e.getMessage(), e.getCause());
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
		List<Device> deviceInfoList = getDevicesFromCache();
		if (deviceInfoList != null) {
			return deviceInfoList;
		} else {
			try {
				deviceInfoList = deviceService.getDeviceInfoList(user);
				saveDevicesToCache(deviceInfoList);
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
			deviceInfo.setAccountNo(account.getAccountNo());
			clearDevicesFromCache();
			return deviceService.addDeviceInfo(user, deviceInfo);
		} catch (DeviceServiceException e) {
			throw new DeviceManagementException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public List<Device> removeDeviceInfo(Device deviceInfo, Account account, User user) throws DeviceManagementException {
		try {
			clearDevicesFromCache();
			return deviceService.deleteDeviceInfo(user, deviceInfo);
		} catch (DeviceServiceException e) {
			throw new DeviceManagementException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public void updateDeviceInfo(User user, Device deviceInfo) throws DeviceManagementException {
		try {
			clearDevicesFromCache();
			deviceService.updateDeviceInfo(user, deviceInfo);
		} catch (DeviceServiceException e) {
			throw new DeviceManagementException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public NetworkInfo swapDevice(User user, Device oldDeviceInfo, Device newDeviceInfo) throws DeviceManagementException {
		try {
			clearDevicesFromCache();
			NetworkInfo oldNetworkInfo = deviceService.getNetworkInfo(oldDeviceInfo.getValue(), null);
			NetworkInfo newNetworkInfo = deviceService.swapDevice(user, oldNetworkInfo, newDeviceInfo);
			return newNetworkInfo;
		} catch (DeviceServiceException e) {
			throw new DeviceManagementException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public NetworkInfo activateService(NetworkInfo networkInfo, User user) throws DeviceActivationException {
		try {
			return deviceService.activateService(user, networkInfo);
		} catch (DeviceServiceException e) {
			throw new DeviceActivationException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public void suspendService(int userId, int accountNo, int deviceId) throws DeviceManagementException {
		try {
			clearDevicesFromCache();
			deviceService.suspendService(userId, accountNo, deviceId);
		} catch (DeviceServiceException e) {
			throw new DeviceManagementException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public void restoreService(int userId, int accountNo, int deviceId) throws DeviceManagementException {
		try {
			clearDevicesFromCache();
			deviceService.restoreService(userId, accountNo, deviceId);
		} catch (DeviceServiceException e) {
			throw new DeviceManagementException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public Account createServiceInstance(Account account, NetworkInfo networkInfo) throws DeviceServiceCreationException {
		try {
			ServiceInstance serviceInstance = new ServiceInstance();
			serviceInstance.setExternalId(networkInfo.getMdn());
			return deviceService.createServiceInstance(account, serviceInstance);
		} catch (DeviceServiceException e) {
			throw new DeviceServiceCreationException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@Loggable(value = LogLevel.TRACE)
	public void disconnectService(ServiceInstance serviceInstance) throws DeviceManagementException {
		try {
			clearDevicesFromCache();
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
	public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceDisconnectException {
		try {
			clearDevicesFromCache();
			deviceService.disconnectFromNetwork(networkInfo);
		} catch (DeviceServiceException e) {
			throw new DeviceDisconnectException(e.getMessage(), e.getCause());
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
			clearDevicesFromCache();
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

	public static boolean isDec(String esn) {
		return esn.matches("\\d+") && (esn.length() == 11 || esn.length() == 18);
	}

	public static boolean isHex(String esn) {
		return !esn.matches("\\d+") && (esn.length() == 8 || esn.length() == 14);
	}

	/* *************************************************************
	 * CacheManger helper functions
	 * *************************************************************
	 */

	@SuppressWarnings("unchecked")
	private List<Device> getDevicesFromCache() {
		return (List<Device>) CacheManager.get(CacheKey.DEVICES);
	}

	private void clearDevicesFromCache() {
		CacheManager.clear(CacheKey.DEVICES);
	}

	private void saveDevicesToCache(List<Device> deviceInfoList) {
		CacheManager.set(CacheKey.DEVICES, deviceInfoList);
	}

}