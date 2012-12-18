package com.trc.service.email;

import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Deprecated
public class EmailTestClient {

  public void postMail(InternetAddress from, Set<InternetAddress> to, Set<InternetAddress> cc, Set<InternetAddress> bcc, String subject, String message)
      throws MessagingException {

    Properties emailProps = new Properties();
    emailProps.put("mail.smtp.host", "209.127.162.26");
    Session session = Session.getDefaultInstance(emailProps, null);
    session.setDebug(false);

    Message msg = new MimeMessage(session);
    msg.setFrom(from);
    msg.addRecipients(RecipientType.TO, to.toArray(new InternetAddress[0]));
    msg.addRecipients(RecipientType.BCC, bcc.toArray(new InternetAddress[0]));
    msg.addRecipients(RecipientType.CC, cc.toArray(new InternetAddress[0]));
    msg.addHeader("MyHeaderName", "myHeaderValue");
    msg.setSubject(subject);
    msg.setContent(message, "text/html");
    try {
      Transport.send(msg);
    } catch (SendFailedException send_ex) {
      send_ex.printStackTrace();
      Address[] validAddressList = send_ex.getValidSentAddresses();
      Address[] invalidAddressList = send_ex.getInvalidAddresses();
      String invalidEmailString = "";
      for (Address address : invalidAddressList) {
        invalidEmailString += address.toString() + "; ";
      }
      AddressException address_ex = new AddressException("The Following Email Address is invalid :: " + invalidEmailString);
      throw address_ex;
    }
  }
}