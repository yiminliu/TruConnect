package com.trc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tscp.mvne.jms.KenanRequestSender;
import com.tscp.mvne.request.kenan.truconnect.AccountRequest;

@Controller
@RequestMapping("/test/jms")
public class JmsTestController {
  private KenanRequestSender sender = new KenanRequestSender();

  @RequestMapping(value = "/accountRequest", method = RequestMethod.GET)
  public String sendAccountRequest() {
    AccountRequest request = new AccountRequest();
    request.getTruConnectAccount().setUserId(599);
    request.getContactInfo().setFirstName("Happy");
    request.getContactInfo().setLastName("Dude");
    request.getContactInfo().setEmail("jmstest@truconnect.com");
    request.getContactInfo().setPhoneNumber("1234567890");
    request.getContactInfo().getAddress().setAddress1("355 South Grand Ave");
    request.getContactInfo().getAddress().setCity("Los Angeles");
    request.getContactInfo().getAddress().setState("CA");
    request.getContactInfo().getAddress().setZip("90071");
    sender.send(request);
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
