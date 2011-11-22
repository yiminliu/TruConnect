package com.trc.web.controller;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.management.PaymentManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.DeviceManager;
import com.trc.manager.PaymentManager;
import com.trc.manager.UserManager;
import com.trc.manager.webflow.PaymentFlowManager;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.service.jms.QueueSender;
import com.trc.service.jms.message.KenanServiceInstance;
import com.trc.service.jms.message.NetworkActivation;
import com.trc.user.User;
import com.trc.util.ClassUtils;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

@Controller
@RequestMapping("/test")
@SuppressWarnings("unused")
public class TestController {
	// @Autowired
	private Destination networkQueue;
	// @Autowired
	private Destination kenanQueue;
	@Autowired
	private PaymentFlowManager paymentFlowManager;
	@Autowired
	private PaymentManager paymentManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private TruConnectGateway truConnectGateway;

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public String testNotifications() {
		User user = userManager.getUserByEmail("jonathan.pong@gmail.com");
		Account account = new Account();
		try {
			account = accountManager.getAccounts(user).get(0);
		} catch (AccountManagementException e) {
			e.printStackTrace();
		}

		truConnectGateway.getPort().sendWelcomeNotification(TruConnectUtil.toCustomer(user), account);
		return "test/success";
	}

	@RequestMapping(value = "/pccharge", method = RequestMethod.GET)
	public String testPcCharge() {
		try {
			User user = userManager.getUserByUsername("jonathan.pong@gmail.com");
			user.setUserId(547);
			System.out.println(ClassUtils.toString(user));
			List<Account> accounts = accountManager.getAccounts(user);
			for (Account account : accounts) {
				System.out.println(ClassUtils.toString(account));
			}

			Account account = accountManager.getAccounts(user).get(0);
			System.out.println(ClassUtils.toString(account));

			CreditCard creditCard = new CreditCard();
			creditCard.setNameOnCreditCard("Happy Dude");
			creditCard.setAddress1("1234 test st");
			creditCard.setCity("test city");
			creditCard.setState("CA");
			creditCard.setZip("85284");

			creditCard.setCreditCardNumber("4387755555555550");
			creditCard.setExpirationDate("1211");
			creditCard.setVerificationcode("999");

			paymentManager.makePayment(user, account, creditCard, "10.00");
			// paymentFlowManager.makeActivationPayment(user, account, creditCard);
		} catch (AccountManagementException e) {
			System.out.println("asdf");
			e.printStackTrace();
		} catch (PaymentManagementException e) {
			System.out.println("qwerty");
			e.printStackTrace();
		}
		return "test/success";
	}

	@RequestMapping(value = "/jms", method = RequestMethod.GET)
	public String testJms() {
		// try {
		// //queueSender.send(generateNetworkActivation(), networkQueue);
		// //queueSender.send(generateKenanActivation(), kenanQueue);
		// } catch (JMSException e) {
		// e.printStackTrace();
		// }
		return "test/success";
	}

	private NetworkActivation generateNetworkActivation() throws JMSException {
		User user = new User();
		user.setUsername("Super King");
		NetworkInfo networkInfo = new NetworkInfo();
		networkInfo.setEsnmeiddec("01234567891");
		networkInfo.setMdn("1234567");
		networkInfo.setStatus("test case");
		NetworkActivation truConnectActivation = new NetworkActivation();
		truConnectActivation.setUser(user);
		truConnectActivation.setNetworkInfo(networkInfo);
		return truConnectActivation;
	}

	private KenanServiceInstance generateKenanActivation() throws JMSException {
		Account account = new Account();
		account.setAccountno(123456);
		account.setFirstname("Captain");
		account.setLastname("Yesterday");
		NetworkInfo networkInfo = new NetworkInfo();
		networkInfo.setEsnmeiddec("01234567891");
		networkInfo.setMdn("1234567");
		networkInfo.setStatus("test case");
		KenanServiceInstance kenanActivation = new KenanServiceInstance();
		kenanActivation.setAccount(account);
		kenanActivation.setNetworkInfo(networkInfo);
		return kenanActivation;
	}
}