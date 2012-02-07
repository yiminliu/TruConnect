package com.trc.service.jms.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tscp.mvne.PaymentRequest;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms/PaymentPost") }, mappedName = "jms/PaymentPost")
public class PaymentTopicSubscriber implements MessageListener {
  protected final Logger devLogger = LoggerFactory.getLogger("devLogger");
  protected final Logger logger = LoggerFactory.getLogger("paymentService");

  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      ObjectMessage objectMessage = (ObjectMessage) message;
      try {
        if (objectMessage.getObject() instanceof PaymentRequest) {
          PaymentRequest request = (PaymentRequest) objectMessage.getObject();
          if (isSuccessfulResponse(request)) {
            logger.info("... success message received by TruConnect from topic for User " + request.getCustomerId()
                + " Account " + request.getAccountNumber());
            devLogger.debug("success message received by TruConnect from topic for User " + request.getCustomerId()
                + " Account " + request.getAccountNumber());
            System.out.println("TC! payment made and received successfully. Confirmation code is "
                + request.getConfirmationCode());
          }
        }
      } catch (JMSException jms_ex) {
        devLogger.error("JMS exception while retrieving message from Kenan Success Topic");
      }
    }
  }

  private boolean isSuccessfulResponse(PaymentRequest request) {
    return !request.getConfirmationCode().equals("-1");
  }
}
