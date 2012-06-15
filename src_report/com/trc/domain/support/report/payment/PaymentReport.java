package com.trc.domain.support.report.payment;

import java.util.List;

import com.trc.user.User;
import com.trc.util.Paginator;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;

public class PaymentReport{

	private PaymentTransaction paymentTransaction;
	private User user;
	private Account account;
	private Device device;
	private int failedPaymentCount;
		
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
	public PaymentTransaction getPaymentTransaction() {
		return paymentTransaction;
	}
	public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
		this.paymentTransaction = paymentTransaction;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
		
	public int getFailedPaymentCount() {
		return failedPaymentCount;
	}
	
	public void setFailedPaymentCount(int failedPaymentCount) {
		this.failedPaymentCount = failedPaymentCount;
	}
	
	public PaymentReport(){}	
	
}
