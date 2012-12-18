package com.trc.user.activation.simple;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.trc.coupon.Coupon;
import com.trc.exception.EmailException;
import com.trc.exception.WebFlowException;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.AddressManagementException;
import com.trc.exception.management.CouponDoesNotExistException;
import com.trc.exception.management.CouponManagementException;
import com.trc.exception.management.DeviceActivationException;
import com.trc.exception.management.DeviceDisconnectException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.management.DeviceReservationException;
import com.trc.exception.management.DeviceServiceCreationException;
import com.trc.exception.management.PaymentManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.AddressManager;
import com.trc.manager.CouponManager;
import com.trc.manager.DeviceManager;
import com.trc.manager.PaymentManager;
import com.trc.manager.UserManager;
import com.trc.service.email.VelocityEmailService;
import com.trc.service.gateway.TSCPMVNE;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.user.contact.Address;
import com.trc.user.contact.ContactInfo;
import com.trc.web.flow.util.WebFlowUtil;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkException_Exception;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.PaymentUnitResponse;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

@Component
public class SimpleActivationManager {
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private PaymentManager paymentManager;
	@Autowired
	private CouponManager couponManager;
	@Autowired
	private VelocityEmailService velocityEmailService;

	static final Logger logger = LoggerFactory.getLogger("devLogger");

	public static void main(String[] args) throws NetworkException_Exception {
		TruConnectGateway trcg = new TruConnectGateway();
		trcg.init();

		System.out.println(TSCPMVNE.location);

		TruConnect trc = trcg.getPort();

		Account account = trc.getAccountInfo(708329);
		System.out.println(account.getAccountNo());
	}

	/* ***********************************************************************
	 * Decision and Fetch Methods
	 * ***********************************************************************
	 */

	public boolean isListEmpty(List<Object> list) {
		return list == null || list.isEmpty();
	}

	public boolean hasCoupon(Coupon coupon) {
		boolean result = coupon != null && !coupon.isEmpty();
		logger.trace("coupon entered... {}", result);
		return result;
	}

	public List<Device> fetchDevices(User user) {
		try {
			List<Device> devices = deviceManager.getDeviceInfoList(user);
			logger.trace("fetching existing devices... {} found", devices.size());
			return devices;
		} catch (DeviceManagementException e) {
			return null;
		}
	}

	public List<Account> fetchAccounts(User user) {
		try {
			List<Account> accounts = accountManager.getAccounts(user);
			logger.trace("fetching existing accounts... {} found", accounts.size());
			return accounts;
		} catch (AccountManagementException e) {
			return null;
		}
	}

	public List<CreditCard> fetchPaymentMethods(User user) throws PaymentManagementException {
		List<CreditCard> creditCards = paymentManager.getCreditCards(user);
		logger.trace("fetching existing payment methods... {} found", creditCards.size());
		return creditCards;
	}

	/* ***********************************************************************
	 * Account Creation Methods
	 * ***********************************************************************
	 */

	public Account createShellAccount(SimpleActivation sa) throws AccountManagementException, AddressManagementException {
		logger.trace("Creating shell account.");

		if (sa == null)
			logger.debug("simpleActvation is null");
		if (sa.getCreditCardPayment() == null)
			logger.debug("sa.creditCardPayment is null");

		CreditCard creditCard = sa.getCreditCardPayment().getCreditCard();
		String firstName = creditCard.getNameOnCreditCard().substring(0, creditCard.getNameOnCreditCard().lastIndexOf(" "));
		String lastName = creditCard.getNameOnCreditCard().substring(creditCard.getNameOnCreditCard().lastIndexOf(" ") + 1);

		Address address = new Address();
		address.setAddress1(creditCard.getAddress1());
		address.setAddress2(creditCard.getAddress2());
		address.setCity(creditCard.getCity());
		address.setState(creditCard.getState());
		address.setZip(creditCard.getZip());
		address.setDefault(true);

		ContactInfo contactInfo = sa.getUser().getContactInfo();
		contactInfo.setFirstName(firstName);
		contactInfo.setLastName(lastName);
		contactInfo.setEmail(sa.getUser().getEmail());
		contactInfo.setPhoneNumber(sa.getCreditCardPayment().getPhoneNumber());
		contactInfo.setAddress(address);

		Account account = accountManager.createShellAccount(sa.getUser());
		logger.debug("Shell account created with number " + account.getAccountNo());
		return account;
	}

	/* ***********************************************************************
	 * Device Methods
	 * ***********************************************************************
	 */

	public String getSuggestedName(String firstName) {
		return firstName + "'s Device";
	}

	public Device addDevice(SimpleActivation sa) throws DeviceManagementException {
		logger.trace("adding device with ESN {} to user {}", sa.getDevice().getValue(), sa.getUser().getEmail());
		sa.setDevice(deviceManager.addDeviceInfo(sa.getDevice(), sa.getAccount(), sa.getUser()));
		return sa.getDevice();
	}

	public List<Device> removeDevice(SimpleActivation sa) throws DeviceManagementException {
		return deviceManager.removeDeviceInfo(sa.getDevice(), sa.getAccount(), sa.getUser());
	}

	public void releaseMdn(SimpleActivation sa) throws DeviceDisconnectException {
		logger.trace("releasing MDN {} for failed activation", sa.getNetworkInfo().getMdn());
		releaseMdn(sa, 3, 1000l);
	}

	protected void releaseMdn(SimpleActivation sa, int numRetry, long delay) throws DeviceDisconnectException {
		logger.trace("releasing MDN, {} attempts left", numRetry);
		if (numRetry > 0) {
			try {
				if (delay > 0)
					Thread.sleep(delay);
				deviceManager.disconnectFromNetwork(sa.getNetworkInfo());
				sa.setNetworkInfo(null);
			} catch (InterruptedException e) {
				throw new DeviceDisconnectException("Thread sleep interrupted. Disconnect never reached.");
			} catch (DeviceDisconnectException e) {
				releaseMdn(sa, --numRetry, delay);
			}
		} else {
			throw new DeviceDisconnectException("Max number of tries reached. MDN " + sa.getNetworkInfo().getMdn() + " not released.");
		}
	}

	public NetworkInfo reserveMdn(SimpleActivation sa) throws DeviceReservationException {
		NetworkInfo networkInfo = reserveMdn(sa, 3, 1000l);
		logger.trace("reserved MDN {} for activation", networkInfo.getMdn());
		return networkInfo;
	}

	protected NetworkInfo reserveMdn(SimpleActivation sa, int numRetry, long delay) throws DeviceReservationException {
		logger.trace("reserving MDN, {} attempts left", numRetry);
		if (numRetry > 0) {
			try {
				if (delay > 0)
					Thread.sleep(delay);
				sa.setNetworkInfo(deviceManager.reserveMdn());
				return sa.getNetworkInfo();

			} catch (InterruptedException e) {
				throw new DeviceReservationException("Thread sleep interrupted. MDN not reserved.");
			} catch (DeviceReservationException e) {
				return reserveMdn(sa, --numRetry, delay);
			}
		} else {
			throw new DeviceReservationException("Max number of tries reached. MDN not reserved for account " + sa.getAccount().getAccountNo());
		}
	}

	public NetworkInfo activateService(SimpleActivation sa) throws DeviceActivationException {
		if (DeviceManager.isDec(sa.getDevice().getValue())) {
			sa.getNetworkInfo().setEsnmeiddec(sa.getDevice().getValue());
		} else if (DeviceManager.isHex(sa.getDevice().getValue())) {
			sa.getNetworkInfo().setEsnmeidhex(sa.getDevice().getValue());
		} else {
			throw new DeviceActivationException("Invalid ESN. Must be either hex or decimal");
		}
		return activateService(sa, 3, 1000l);
	}

	protected NetworkInfo activateService(SimpleActivation sa, int numRetry, long delay) throws DeviceActivationException {
		logger.trace("activating service, {} attempts left", numRetry);
		if (numRetry > 0) {
			try {

				if (delay > 0)
					Thread.sleep(delay);
				sa.setNetworkInfo(deviceManager.activateService(sa.getNetworkInfo(), sa.getUser()));
				return sa.getNetworkInfo();

			} catch (InterruptedException e) {
				throw new DeviceActivationException("Thread sleep interrupted. Activation never reached");
			} catch (DeviceActivationException e) {
				return activateService(sa, --numRetry, delay);
			}
		} else {
			throw new DeviceActivationException("Max number of tries reached. Activation not completed for account " + sa.getAccount().getAccountNo() + " on ESN "
					+ sa.getDevice().getValue() + " with MDN " + sa.getNetworkInfo().getMdn());
		}
	}

	public Account createService(SimpleActivation sa) throws DeviceServiceCreationException {
		sa.setAccount(createService(sa, 3, 1000l));
		return sa.getAccount();
	}

	protected Account createService(SimpleActivation sa, int numRetry, long delay) throws DeviceServiceCreationException {
		logger.trace("creating service in Kenan, {} attempts left", numRetry);
		try {
			if (numRetry > 0) {
				if (delay > 0)
					Thread.sleep(delay);
				sa.setAccount(deviceManager.createServiceInstance(sa.getAccount(), sa.getNetworkInfo()));
				return sa.getAccount();
			}
			throw new DeviceServiceCreationException("Max number of tries reached. Service not created for account " + sa.getAccount().getAccountNo() + " with MDN "
					+ sa.getNetworkInfo().getMdn());
		} catch (InterruptedException e) {
			throw new DeviceServiceCreationException("Thread sleep interrupted. Service not created for account " + sa.getAccount().getAccountNo() + " with MDN "
					+ sa.getNetworkInfo().getMdn());
		} catch (DeviceServiceCreationException e) {
			return createService(sa, --numRetry, delay);
		}
	}

	public NetworkInfo reserveAndActivate(SimpleActivation sa) throws DeviceReservationException, DeviceActivationException, DeviceDisconnectException,
			DeviceManagementException {
		try {
			reserveMdn(sa);
			return activateService(sa);
		} catch (DeviceReservationException e) {
			throw e;
		} catch (DeviceActivationException e) {
			throw e;
		}
	}

	/* ***********************************************************************
	 * Coupon Methods
	 * ***********************************************************************
	 */

	private static String ERROR_APPLY_COUPON = "An error occurred while applying your coupon. No changes were made, please try again.";

	public Coupon getCouponByCode(Coupon coupon) throws CouponManagementException, CouponDoesNotExistException {
		String code = coupon.getCouponCode();
		logger.debug("fetching coupon {}", code);
		Coupon fetchedCoupon = couponManager.getCouponByCode(code);
		if (fetchedCoupon == null) {
			logger.debug("coupon {} does not exist", code);
			throw new CouponDoesNotExistException("Coupon " + code + " does not exist");
		} else {
			return fetchedCoupon;
		}
	}

	public int applyCoupon(SimpleActivation sa) throws CouponManagementException {
		Coupon coupon = sa.getCreditCardPayment().getCoupon();
		if (coupon != null && !coupon.isEmpty()) {
			if (coupon.getCouponDetail().getContract().getContractType() == -1) {
				logger.trace("applying coupon {}", sa.getCreditCardPayment().getCoupon().getCouponCode());
				ServiceInstance serviceInstance = new ServiceInstance();
				serviceInstance.setExternalId("");
				return couponManager.applyCoupon(sa.getCreditCardPayment().getCoupon(), sa.getUser(), sa.getAccount(), serviceInstance);
			} else {
				logger.trace("no coupon to apply");
			}
		}
		logger.trace("no coupon to apply");
		return 0;
	}

	public int applyContract(SimpleActivation sa) throws CouponManagementException {
		Coupon coupon = sa.getCreditCardPayment().getCoupon();
		if (coupon != null && !coupon.isEmpty()) {
			if (coupon.getCouponDetail().getContract().getContractType() != -1) {
				logger.debug("applying contract {}", sa.getCreditCardPayment().getCoupon().getCouponCode());
				ServiceInstance serviceInstance = new ServiceInstance();
				serviceInstance.setExternalId(sa.getNetworkInfo().getMdn());
				return couponManager.applyCoupon(sa.getCreditCardPayment().getCoupon(), sa.getUser(), sa.getAccount(), serviceInstance);
			} else {
				logger.trace("no contract to apply");
			}
		}
		logger.trace("no contract to apply");
		return 0;
	}

	/* ***********************************************************************
	 * Payment Methods
	 * ***********************************************************************
	 */

	public void saveOrUpdatePaymentMethod(User user, CreditCard creditCard) throws PaymentManagementException {
		if (creditCard.getPaymentid() == 0)
			savePaymentMethod(user, creditCard);
		else
			updatePaymentMethod(user, creditCard);
	}

	public void savePaymentMethod(User user, CreditCard creditCard) throws PaymentManagementException {
		logger.debug("Adding payment method for user " + userManager.getCurrentUser().getEmail() + " " + creditCard.getCreditCardNumber());
		CreditCard createdCreditCard = paymentManager.addCreditCard(user, creditCard);
		TruConnectUtil.copyCreditCard(creditCard, createdCreditCard);
	}

	public void updatePaymentMethod(User user, CreditCard creditCard) throws PaymentManagementException {
		logger.debug("Updating payment method for user " + userManager.getCurrentUser().getEmail() + " " + creditCard.getCreditCardNumber());
		paymentManager.updateCreditCard(user, creditCard);
	}

	public CreditCard getPaymentMethod(int paymentId) throws PaymentManagementException {
		CreditCard cc = paymentManager.getCreditCard(paymentId);
		logger.trace("fetching payment method with ID {} and value {}", paymentId, cc.getCreditCardNumber());
		return cc;
	}

	public CreditCard getDefaultPaymentMethod(User user) throws PaymentManagementException {
		logger.debug("fetching default payment method for {}", user.getEmail());
		return paymentManager.getDefaultCreditCard(user);
	}

	public PaymentUnitResponse makeActivationPayment(SimpleActivation sa) throws PaymentManagementException {
		User user = sa.getUser();
		Account account = sa.getAccount();
		CreditCard cc = sa.getCreditCardPayment().getCreditCard();
		Coupon coupon = sa.getCreditCardPayment().getCoupon();

		logger.trace("making activation payment for {} on account {} with card {}",
				new Object[] { user.getEmail(), account.getAccountNo(), cc.getCreditCardNumber() });

		double amount = 10.00;
		if (coupon != null && !coupon.isEmpty())
			if (coupon.getCouponDetail().getContract().getContractType() == -1) {
				amount = amount - coupon.getCouponDetail().getAmount();
				logger.trace("coupon applied, remaining activaiton fee of {}", amount);
			}

		if (amount > 0.0) {
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String moneyString = formatter.format(amount);
			logger.debug("skipping activation payment temporarily");
			return new PaymentUnitResponse();
			// return paymentManager.makePayment(user, account, cc.getPaymentid(),
			// moneyString.substring(1));
		} else {
			return new PaymentUnitResponse();
		}

	}

	/* ***********************************************************************
	 * Helper Methods
	 * ***********************************************************************
	 */

	public void addFlowError(String message) {
		WebFlowUtil.addError(message);
	}

	public void addFlowError(String code, String message) {
		WebFlowUtil.addError(code, message);
	}

	public void addFlowError(String source, String code, String message) {
		WebFlowUtil.addError(source, code, message);
	}

	public void trace(String pattern, Object obj) {
		logger.trace(pattern, obj);
	}

	public void printStatus(SimpleActivation simpleActivation) {
		if (simpleActivation != null) {
			logger.debug("activation state:");
			if (simpleActivation.getUser() != null)
				logger.debug("  user: {} {}", simpleActivation.getUser().getUserId(), simpleActivation.getUser().getEmail());
			if (simpleActivation.getCreditCardPayment() != null) {
				logger.debug("  cc: {} {}", simpleActivation.getCreditCardPayment().getCreditCard().getPaymentid(), simpleActivation.getCreditCardPayment()
						.getCreditCard().getCreditCardNumber());
			}
			if (simpleActivation.getDevice() != null)
				logger.debug("  device: {} {}", simpleActivation.getDevice().getId(), simpleActivation.getDevice().getValue());
			if (simpleActivation.getAccount() != null)
				logger.debug("  account: {}", simpleActivation.getAccount().getAccountNo());
		}
	}

	public void sendAccountNotice(SimpleRegistration sr) {
		logger.trace("sending account creation notice to user");
		logger.debug("skipping notification temporarily");
	}

	public void sendActivationNotice(SimpleActivation sa) {
		logger.trace("sending activation notice to user");
		logger.debug("skipping notification temporarily");
	}

	public void sendDisconnectExceptionNotice(SimpleActivation sa) {
		try {
			SimpleMailMessage myMessage = new SimpleMailMessage();
			myMessage.setTo("truconnect_alerts@telscape.net");
			myMessage.setFrom("system-activations@truconnect.com");
			myMessage.setSubject("Exception while disconnecting MDN " + sa.getNetworkInfo().getMdn());
			Map<Object, Object> mailModel = new HashMap<Object, Object>();
			mailModel.put("dateTime", new Date());
			mailModel.put("email", sa.getUser().getEmail());
			mailModel.put("userId", sa.getUser().getUserId());
			mailModel.put("accountNo", sa.getAccount().getAccountNo());
			mailModel.put("mdn", sa.getNetworkInfo().getMdn());
			mailModel.put("esn", sa.getNetworkInfo().getEsnmeiddec());
			velocityEmailService.send("error_test_activation", myMessage, mailModel);
		} catch (EmailException e) {
			// do nothing
		}
	}

	public void notifyReleaseMdnException(SimpleActivation sa) {
		try {
			SimpleMailMessage myMessage = new SimpleMailMessage();
			myMessage.setTo("truconnect_alerts@telscape.net");
			myMessage.setFrom("system-activations@truconnect.com");
			myMessage.setSubject("Exception while releasing a reserved MDN on a failed activation " + sa.getNetworkInfo().getMdn());
			Map<Object, Object> mailModel = new HashMap<Object, Object>();
			mailModel.put("dateTime", new Date());
			mailModel.put("email", sa.getUser().getEmail());
			mailModel.put("userId", sa.getUser().getUserId());
			mailModel.put("accountNo", sa.getAccount().getAccountNo());
			mailModel.put("mdn", sa.getNetworkInfo().getMdn());
			mailModel.put("esn", sa.getNetworkInfo().getEsnmeiddec());
			velocityEmailService.send("error_test_activation", myMessage, mailModel);
		} catch (EmailException e) {
			// do nothing
		}
	}

	public void sendServiceErrorNotice(SimpleActivation sa) throws WebFlowException {
		try {
			SimpleMailMessage myMessage = new SimpleMailMessage();
			myMessage.setTo("truconnect_alerts@telscape.net");
			myMessage.setFrom("system-activations@truconnect.com");
			myMessage.setSubject("Exception while creating service for Account " + sa.getAccount().getAccountNo());
			Map<Object, Object> mailModel = new HashMap<Object, Object>();
			mailModel.put("dateTime", new Date());
			mailModel.put("email", sa.getUser().getEmail());
			mailModel.put("userId", sa.getUser().getUserId());
			mailModel.put("accountNo", sa.getAccount().getAccountNo());
			mailModel.put("mdn", sa.getNetworkInfo().getMdn());
			mailModel.put("esn", sa.getNetworkInfo().getEsnmeiddec());
			velocityEmailService.send("error_create_service", myMessage, mailModel);
		} catch (EmailException e) {
			// do nothing
		}
	}

}
