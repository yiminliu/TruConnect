package com.trc.service.jms.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.trc.account.TruConnectAccount;
import com.tscp.mvne.request.kenan.truconnect.AccountRequest;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms/KenanPost") }, mappedName = "jms/KenanPost")
public class KenanTopicSubscriber implements MessageListener {

  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      ObjectMessage objectMessage = (ObjectMessage) message;
      try {
        if (objectMessage.getObject() instanceof AccountRequest) {
          AccountRequest request = (AccountRequest) objectMessage.getObject();
          if (isSuccessfulResponse(request)) {
            System.out.println("TC! account created and received successfully");
          }
        }
      } catch (JMSException jms_ex) {

      }
    }
  }

  private boolean isSuccessfulResponse(AccountRequest request) {
    TruConnectAccount account = request.getTruConnectAccount();
    return account.getAccountNumber() > 0 && account.getUserId() > 0;
  }
}