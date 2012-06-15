
package com.trc.dao;

import java.util.Collection;
import java.util.Date;

import com.trc.domain.support.report.payment.PaymentTransaction;

public interface PaymentTransactionDao {

	public Collection<PaymentTransaction> getAllPaymentTransactions();
	
	public Collection<Integer> getAllAccountNumbers();		
	
	public Collection<PaymentTransaction> getFailedPaymentTransactions();
		
	public Collection<PaymentTransaction> getFailedPaymentTransactionByCustId(int custId);
	
	public Collection<PaymentTransaction> getFailedPaymentTransactionByAccountNo(int accountNo);
	
	public PaymentTransaction getFailedPaymentTransactionByAccountByTransId(int transId);
	
	public Collection<PaymentTransaction> getFailedPaymentTransactionByDate(Date beginDate, Date endDate);	
	
	public Collection<PaymentTransaction> getFailedPaymentTransactionByDate(Date beginDate, Date endDate, int numOfRecords);	
	
}
