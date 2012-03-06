package com.trc.manager.impl;

import org.springframework.stereotype.Component;

import com.trc.user.activation.Registration;
import com.tscp.mvne.AccountRequest;
import com.tscp.mvne.AccountRequestType;
import com.tscp.mvne.jms.account.AccountJmsProvider;
import com.tscp.mvne.jms.account.AccountRequestSender;
import com.tscp.mvne.jms.network.NetworkJmsProvider;
import com.tscp.mvne.jms.network.NetworkRequestSender;
import com.tscp.mvne.jms.payment.PaymentJmsProvider;
import com.tscp.mvne.jms.payment.PaymentRequestSender;

/**
 * This class is responsible for sending collected information to MDBs using JMS
 * for account creation.
 * 
 * @author Tachikoma
 * 
 */
@Component
public class ActivationManager {
  private static final AccountRequestSender accountRequestSender = AccountJmsProvider.getRequestSenderInstance();
  private static final PaymentRequestSender paymentRequestSender = PaymentJmsProvider.getRequestSenderInstance();
  private static final NetworkRequestSender networkRequestSender = NetworkJmsProvider.getRequestSenderInstance();

  public void makeAccountRequest(Registration registration) {
    AccountRequest request = new AccountRequest();
    request.setContactInfo(ActivationUtil.getContactInfo(registration.getUser().getUserId(), registration.getUser()
        .getContactInfo()));
    request.setCustomerId(registration.getUser().getUserId());
    request.setRequestType(AccountRequestType.SHELL);
  }

}
