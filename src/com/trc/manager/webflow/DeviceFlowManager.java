package com.trc.manager.webflow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.trc.exception.EmailException;
import com.trc.exception.WebFlowException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.manager.impl.DeviceManager;
import com.trc.service.email.VelocityEmailService;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.web.flow.util.WebFlowUtil;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;

/**
 * Some methods sleep for 500ms to allow for Sprint API calls to complete. It is
 * unsure if this is necessary.
 * 
 * @author Tachikoma
 * 
 */
@Component
public class DeviceFlowManager {
  @Autowired
  private DeviceManager deviceManager;
  @Autowired
  private VelocityEmailService velocityEmailService;

  private static final int sleepTime = 750;
  private static final String ERROR_RESERVE_MDN = "An error occured while reserving your Mobile Data card. Please try entering your ESN again.";
  private static final String ERROR_ACTIVATE = "An error occured while activating your card. Please enter your ESN again.";
  private static final String ERROR_REMOVE_DEVICE = "An error occurred while clearing your device information. No changes were made. Please try again.";
  private static final String ERROR_DISCONNECT = "An error occurred while testing your device's ability to disconnect. No changes were made. Please try again.";
  private static final String ERROR_CREATE_SERVICE = "An error occurred while preparing your device's service. No changes were made. Please try again.";

  public void setDefaultDeviceLabel(Device deviceInfo, String firstName) {
    deviceManager.setDefaultDeviceLabel(deviceInfo, firstName);
  }

  public void reserveMdn(NetworkInfo networkInfo) throws WebFlowException {
    try {
      NetworkInfo reservedNetworkInfo = deviceManager.reserveMdn();
      TruConnectUtil.copyNetworkInfo(networkInfo, reservedNetworkInfo);
      Thread.sleep(sleepTime);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_RESERVE_MDN);
      throw new WebFlowException(e.getMessage(), e.getCause());
    } catch (InterruptedException e) {
      WebFlowUtil.addError(ERROR_RESERVE_MDN);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void bindEsn(NetworkInfo networkInfo, Device deviceInfo) {
    deviceManager.bindEsn(networkInfo, deviceInfo);
  }

  public void addDeviceInfo(Device deviceInfo, Account account, User user) throws WebFlowException {
    try {
      Device newDeviceInfo = deviceManager.addDeviceInfo(deviceInfo, account, user);
      TruConnectUtil.copyDeviceInfo(deviceInfo, newDeviceInfo);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void removeDeviceInfo(Device deviceInfo, Account account, User user) throws WebFlowException {
    try {
      deviceManager.removeDeviceInfo(deviceInfo, account, user);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_REMOVE_DEVICE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void activateService(NetworkInfo networkInfo, User user) throws WebFlowException {
    try {
      NetworkInfo newNetworkInfo = deviceManager.activateService(networkInfo, user);
      TruConnectUtil.copyNetworkInfo(networkInfo, newNetworkInfo);
      Thread.sleep(sleepTime);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    } catch (InterruptedException e) {
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void deactivateService(Account account) throws WebFlowException {
    try {
      deviceManager.disconnectService(account);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void disconnectFromKenan(Account account) throws WebFlowException {
    try {
      deviceManager.disconnectFromKenan(account, account.getServiceinstancelist().get(0));
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_DISCONNECT);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void disconnectService(NetworkInfo networkInfo) throws WebFlowException {
    try {
      Thread.sleep(sleepTime);
      deviceManager.disconnectFromNetwork(networkInfo);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_DISCONNECT);
      throw new WebFlowException(e.getMessage(), e.getCause());
    } catch (InterruptedException e) {
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void createServiceInstance(Account account, NetworkInfo networkInfo) throws WebFlowException {
    try {
      Account result = deviceManager.createServiceInstance(account, networkInfo);
      TruConnectUtil.copyAccount(account, result);
      Thread.sleep(sleepTime);
    } catch (DeviceManagementException e) {
      WebFlowUtil.addError(ERROR_CREATE_SERVICE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    } catch (InterruptedException e) {
      WebFlowUtil.addError(ERROR_CREATE_SERVICE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void sendDisconnectErrorNotice(User user, Device device, Account account, NetworkInfo networkInfo) throws WebFlowException {
    try {
      SimpleMailMessage myMessage = new SimpleMailMessage();
      myMessage.setTo("truconnect_alerts@telscape.net");
      myMessage.setFrom("system-activations@truconnect.com");
      myMessage.setSubject("Exception while disconnecting MDN " + networkInfo.getMdn());
      Map<Object, Object> mailModel = new HashMap<Object, Object>();
      mailModel.put("dateTime", new Date());
      mailModel.put("email", user.getEmail());
      mailModel.put("userId", user.getUserId());
      mailModel.put("accountNo", account.getAccountno());
      mailModel.put("mdn", networkInfo.getMdn());
      mailModel.put("esn", networkInfo.getEsnmeiddec());
      velocityEmailService.send("error_test_activation", myMessage, mailModel);
    } catch (EmailException e) {
      e.printStackTrace();
    }
  }

  public void sendServiceErrorNotice(User user, Device device, Account account, NetworkInfo networkInfo) throws WebFlowException {
    try {
      SimpleMailMessage myMessage = new SimpleMailMessage();
      myMessage.setTo("truconnect_alerts@telscape.net");
      myMessage.setFrom("system-activations@truconnect.com");
      myMessage.setSubject("Exception while creating service for Account " + account.getAccountno());
      Map<Object, Object> mailModel = new HashMap<Object, Object>();
      mailModel.put("dateTime", new Date());
      mailModel.put("email", user.getEmail());
      mailModel.put("userId", user.getUserId());
      mailModel.put("accountNo", account.getAccountno());
      mailModel.put("mdn", networkInfo.getMdn());
      mailModel.put("esn", networkInfo.getEsnmeiddec());
      velocityEmailService.send("error_create_service", myMessage, mailModel);
    } catch (EmailException e) {
      e.printStackTrace();
    }
  }

}