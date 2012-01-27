package com.trc.service.jms.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trc.account.TruConnectAccount;
import com.tscp.mvne.request.kenan.truconnect.AccountRequest;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms/KenanPost") }, mappedName = "jms/KenanPost")
public class KenanTopicSubscriber implements MessageListener {
  // protected final Logger logger = LoggerFactory.getLogger("kenanService");

  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      ObjectMessage objectMessage = (ObjectMessage) message;
      try {
        if (objectMessage.getObject() instanceof AccountRequest) {
          AccountRequest request = (AccountRequest) objectMessage.getObject();
          if (isSuccessfulResponse(request)) {
            // logger.info("... success message received from topic!");
            System.out.println("TC! account created and received successfully. Generated number is "
                + request.getTruConnectAccount().getAccountNumber());
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