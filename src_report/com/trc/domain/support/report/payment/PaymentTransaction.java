package com.trc.domain.support.report.payment;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.trc.security.encryption.SessionEncrypter;

@Entity
@Table(name="pmt_trans")
public class PaymentTransaction implements Serializable {
	  private static final long serialVersionUID = 1495544695293668738L;

  @Id
  @Column(name="trans_id")
  private int transId;
  @Column(name="session_id")
  private String sessionId;
  @Column(name="cust_id", nullable = true)
  private Integer custId;
  @Column(name="pmt_id")
  private Integer pmtId;
  @Column(name="attempt_no")
  private Integer attemptNo;
  @Column(name="pmt_amount")
  private String paymentAmount;
  @Column(name="pmt_trans_date")
  private Date paymentTransDate;
  @Column(name="pmt_unit_confirmation")
  private String paymentUnitConfirmation;
  @Column(name="pmt_unit_date")
  private Date paymentUnitDate;
  @Column(name="pmt_unit_msg")
  private String paymentUnitMessage;
  @Column(name="billing_tracking_id")
  private Integer billingTrackingId = 0;
  @Column(name="billing_unit_date")
  private Date billingUnitDate;
  @Column(name="pmt_source")
  private String paymentSource;
  @Column(name="pmt_method")
  private String paymentMethod;
  @Column(name="account_no")
  private Integer accountNo;
  
  @Transient
  private String encodedAccountNum;
  

  public PaymentTransaction() {
  }

  public int getTransId() {
    return transId;
  }

  public void setTransId(int transId) {
    this.transId = transId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public void setPmtId(Integer pmtId) {
		this.pmtId = pmtId;
	}

	public void setAttemptNo(Integer attemptNo) {
		this.attemptNo = attemptNo;
	}

	public void setBillingTrackingId(Integer billingTrackingId) {
		this.billingTrackingId = billingTrackingId;
	}

	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}

  public int getPmtId() {
    return pmtId;
  }

  public void setPmtId(int pmtId) {
    this.pmtId = pmtId;
  }

  public int getAttemptNo() {
    return attemptNo;
  }

  public void setAttemptNo(int attemptNo) {
    this.attemptNo = attemptNo;
  }

  public String getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(String paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public Date getPaymentTransDate() {
    return paymentTransDate;
  }

  public void setPaymentTransDate(Date paymentTransDate) {
    this.paymentTransDate = paymentTransDate;
  }

  public String getPaymentUnitConfirmation() {
    return paymentUnitConfirmation;
  }

  public void setPaymentUnitConfirmation(String paymentUnitConfirmation) {
    this.paymentUnitConfirmation = paymentUnitConfirmation;
  }

  public Date getPaymentUnitDate() {
    return paymentUnitDate;
  }

  public void setPaymentUnitDate(Date paymentUnitDate) {
    this.paymentUnitDate = paymentUnitDate;
  }

  public String getPaymentUnitMessage() {
    return paymentUnitMessage;
  }

  public void setPaymentUnitMessage(String paymentUnitMessage) {
    this.paymentUnitMessage = paymentUnitMessage;
  }

  public int getBillingTrackingId() {
    return billingTrackingId;
  }

  public void setBillingTrackingId(int billingTrackingId) {
    this.billingTrackingId = billingTrackingId;
  }

  public Date getBillingUnitDate() {
    return billingUnitDate;
  }

  public void setBillingUnitDate(Date billingUnitDate) {
    this.billingUnitDate = billingUnitDate;
  }

  public String getPaymentSource() {
    return paymentSource;
  }

  public void setPaymentSource(String paymentSource) {
    this.paymentSource = paymentSource;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Integer getAccountNo() {
	 return accountNo;
  }

  public void setAccountNo(int accountNo) {
	 this.accountNo = accountNo;
  }

  public String getEncodedAccountNum() {
	if(encodedAccountNum == null)
	   encodedAccountNum = SessionEncrypter.encryptId(accountNo); 
	return encodedAccountNum;
  }

  public void setEncodedAccountNum(String encodedAccountNum) {
	  if(encodedAccountNum == null)
		 this.encodedAccountNum = SessionEncrypter.encryptId(accountNo); 
	  else
	     this.encodedAccountNum = encodedAccountNum;
  }
}
