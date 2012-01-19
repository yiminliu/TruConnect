package com.trc.service.impl;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.GatewayException;
import com.trc.service.TruConnectEmailServiceModel;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.TruConnect;

@Service
public class TruConnectEmailService implements TruConnectEmailServiceModel {
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  @Override
  public void sendActivationEmail(User user, Account account) throws GatewayException {
    try {
      truConnect.sendWelcomeNotification(TruConnectUtil.toCustomer(user), account);
    } catch (WebServiceException e) {
      throw new GatewayException(e.getMessage(), e.getCause());
    }
  }

}
