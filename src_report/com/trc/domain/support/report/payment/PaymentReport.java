package com.trc.domain.support.report.payment;

import java.util.Date;

import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.PaymentTransaction;

public class PaymentReport implements Comparable<PaymentReport> {

	private PaymentTransaction paymentTransaction;
	private User user;
	private Account account;
	private int failedPaymentCount;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public PaymentReport() {
	}

	public int compareTo(PaymentReport paymentReport) {
		Date paymentTransactionDate = paymentReport.getPaymentTransaction().getPaymentTransDate().toGregorianCalendar().getTime();
		return paymentTransactionDate.compareTo(this.paymentTransaction.getPaymentTransDate().toGregorianCalendar().getTime());
	}
}
