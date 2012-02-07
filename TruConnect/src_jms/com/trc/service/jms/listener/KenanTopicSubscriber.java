package com.trc.service.jms.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.ShellAccount;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms/KenanPost") }, mappedName = "jms/KenanPost")
public class KenanTopicSubscriber implements MessageListener {
  protected final Logger devLogger = LoggerFactory.getLogger("devLogger");
  protected final Logger logger = LoggerFactory.getLogger("kenanService");

  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      ObjectMessage objectMessage = (ObjectMessage) message;
      try {
        if (objectMessage.getObject() instanceof ShellAccount) {
          ShellAccount request = (ShellAccount) objectMessage.getObject();
          if (isSuccessfulResponse(request)) {
            logger.info("... success message received by TruConnect from topic for User " + request.getCustomerId()
                + " Account " + request.getAccountNumber());
            devLogger.debug("success message received by TruConnect from topic for User " + request.getCustomerId()
                + " Account " + request.getAccountNumber());
            System.out.println("TC! account created and received successfully. Generated number is "
                + request.getAccountNumber());
          }
        }
      } catch (JMSException jms_ex) {
        devLogger.error("JMS exception while retrieving message from Kenan Success Topic");
      }
    }
  }

  private boolean isSuccessfulResponse(ShellAccount request) {
    return request.getAccountNumber() > 0 && request.getCustomerId() > 0;
  }
}