package com.trc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.service.gateway.TruConnectGateway;
import com.trc.web.session.SessionManager;
import com.tscp.mvne.Contact;
import com.tscp.mvne.ContactInfo;
import com.tscp.mvne.CustomerAddress;
import com.tscp.mvne.CustomerCreditCard;
import com.tscp.mvne.PaymentRequest;
import com.tscp.mvne.ShellAccount;
import com.tscp.mvne.TruConnect;
import com.tscp.mvne.jms.account.KenanRequestSender;
import com.tscp.mvne.jms.payment.PaymentRequestSender;

@Controller
@RequestMapping("/test/jms")
public class JmsTestController {
  private KenanRequestSender sender = new KenanRequestSender();
  private PaymentRequestSender paymentSender = new PaymentRequestSender();
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  @RequestMapping(value = "/accountRequest", method = RequestMethod.GET)
  public String sendAccountRequest() {
    ContactInfo contactInfo = new ContactInfo();
    if (contactInfo.getContact() == null) {
      contactInfo.setContact(new Contact());
    }
    if (contactInfo.getAddress() == null) {
      contactInfo.setAddress(new CustomerAddress());
    }
    contactInfo.getContact().setCustId(599);
    contactInfo.getContact().setFirstName("Happy");
    contactInfo.getContact().setLastName("Dude");
    contactInfo.getContact().setEmail("jmstest@truconnect.com");
    contactInfo.getContact().setPhoneNumber("1234567890");
    contactInfo.getAddress().setAddress1("355 South Grand Ave");
    contactInfo.getAddress().setCity("Los Angeles");
    contactInfo.getAddress().setState("CA");
    contactInfo.getAddress().setZip("90071");
    ShellAccount shellAccount = new ShellAccount();
    shellAccount.setContactInfo(contactInfo);
    shellAccount.setCustomerId(599);
    // save contact info and address. this information can
    // be retrieved later if we need to retry the request.
    truConnect.saveContactInfo(contactInfo);
    // send the request for the account to be built
    sender.send(shellAccount);
    return "test/success";
  }

  @RequestMapping(value = "/paymentRequest", method = RequestMethod.GET)
  public String sendPaymentRequest() {
    PaymentRequest request = new PaymentRequest();
    request.setCustomerId(547);
    request.setAmount(1.00);
    request.setAccountNumber(695482);
    request.setCreditCard(new CustomerCreditCard());
    request.getCreditCard().setPaymentId(1717);
    request.getCreditCard().setCardNumer("4387-XXXX-XXXX-5550");
    request.setSessionId(SessionManager.getCurrentSessionId());
    paymentSender.send(request);
    return "test/success";
  }

  @RequestMapping(value = "/hibernate/paymentInfo", method = RequestMethod.GET)
  public String insertPaymentInfo() {
    // Session session =
    // MVNEHibernateUtil.getSessionFactory().getCurrentSession();
    // Transaction tx = session.beginTransaction();

    // PaymentInfo paymentInfo = new PaymentInfo();
    // paymentInfo.setPaymentId(9999);
    // paymentInfo.getContactInfo().setFirstName("Happy Dude");
    // paymentInfo.getContactInfo().getAddress().setAddress1("355 South Grand Ave");
    // paymentInfo.getContactInfo().getAddress().setCity("Los Angeles");
    // paymentInfo.getContactInfo().getAddress().setState("CA");
    // paymentInfo.getContactInfo().getAddress().setZip("90071");
    //
    // session.saveOrUpdate(paymentInfo);
    // tx.commit();
    // session.close();

    return "test/success";
  }
}
