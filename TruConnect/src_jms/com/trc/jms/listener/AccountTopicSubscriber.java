package com.trc.jms.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.tscp.mvne.AccountRequest;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms/KenanPost") }, mappedName = "jms/KenanPost")
public final class AccountTopicSubscriber implements MessageListener {

  
  @Override
  public final void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      ObjectMessage objectMessage = (ObjectMessage) message;
      try {
        if (objectMessage.getObject() instanceof AccountRequest) {
          AccountRequest req = (AccountRequest) objectMessage.getObject();
          int accountNumber = req.getAccountNumber();
          if (accountNumber > 0) {
            //success
          } else {
            // fail
          }
        }
      } catch (JMSException jms_ex) {
        jms_ex.printStackTrace();
      }
    }
  }

  protected final void processRequest(AccountRequest req) {

  }

}