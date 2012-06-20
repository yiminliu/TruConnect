package com.trc.domain.support.report.payment;

import java.util.List;

import com.trc.user.User;
import com.trc.util.Paginator;

public class FailedPaymentHistory extends Paginator<PaymentReport>{
	
	List<PaymentReport> paymentReports;
	
	public FailedPaymentHistory(){}
	
	public FailedPaymentHistory(List<PaymentReport> paymentReports) {
		 super.setRecords(paymentReports);
		 super.setSummarySize(3);
		 this.paymentReports = paymentReports;
   }

	public List<PaymentReport> getPaymentReports() {
		return paymentReports;
	}

	public void setPaymentReports(List<PaymentReport> paymentReports) {
		this.paymentReports = paymentReports;
	}	
}
