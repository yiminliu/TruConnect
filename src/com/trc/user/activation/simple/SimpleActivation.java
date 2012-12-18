package com.trc.user.activation.simple;

import java.io.Serializable;

import com.trc.user.User;
import com.trc.user.activation.CreditCardPayment;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;
import com.tscp.mvne.NetworkInfo;

public class SimpleActivation implements Serializable {
	private static final long serialVersionUID = 1528032881751342638L;
	private User user;
	private CreditCardPayment creditCardPayment;
	private Account account;
	private Device device;
	private NetworkInfo networkInfo;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CreditCardPayment getCreditCardPayment() {
		return creditCardPayment;
	}

	public void setCreditCardPayment(CreditCardPayment creditCardPayment) {
		this.creditCardPayment = creditCardPayment;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	public void setNetworkInfo(NetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}

	@Override
	public String toString() {
		return "SimpleActivation [\nuser=" + user + ", \ncreditCardPayment=" + creditCardPayment + ", \naccount=" + account + ", \ndevice=" + device + "]";
	}

}
