package com.trc.user.account;

import java.util.ArrayList;
import java.util.List;

import com.trc.exception.management.AccountManagementException;
import com.trc.manager.impl.AccountManager;
import com.trc.security.encryption.SessionEncrypter;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.Device;

/**
 * Overview contains all accountDetails and the paymentHistory for the given
 * user.
 * 
 * @author Tachikoma
 * 
 */
public class Overview {
  private List<AccountDetail> accountDetails;
  private PaymentHistory paymentHistory;

  public Overview(AccountManager accountManager, List<Device> devices, User user) {
    this.accountDetails = new ArrayList<AccountDetail>();
    AccountDetail accountDetail;
    Account account;
    try {
      this.paymentHistory = new PaymentHistory(accountManager.getPaymentRecords(user), user);
      for (Device deviceInfo : devices) {
        account = accountManager.getAccount(deviceInfo.getAccountNo());
        accountDetail = new AccountDetail();
        accountDetail.setAccount(account);
        accountDetail.setDevice(deviceInfo);
        accountDetail.setTopUp(accountManager.getTopUp(user, account).getTopupAmount());
        accountDetail.setUsageHistory(new UsageHistory(accountManager.getChargeHistory(user, account.getAccountNo()), user, account.getAccountNo()));
        this.accountDetails.add(accountDetail);
      }
    } catch (AccountManagementException e) {
      e.printStackTrace();
    }
  }

  public PaymentHistory getPaymentDetails() {
    return paymentHistory;
  }

  public void setPaymentDetails(PaymentHistory paymentDetails) {
    this.paymentHistory = paymentDetails;
  }

  public AccountDetail getAccountDetail(int accountNo) {
    for (AccountDetail accountDetail : accountDetails) {
      if (accountDetail.getAccount().getAccountNo() == accountNo) {
        return accountDetail;
      }
    }
    return null;
  }

  public List<AccountDetail> getAccountDetails() {
    return accountDetails;
  }

  public void setAccountDetails(List<AccountDetail> accountDetails) {
    this.accountDetails = accountDetails;
  }

  public Overview encodeAll() {
    for (AccountDetail accountDetail : accountDetails) {
      accountDetail.setEncodedAccountNum(SessionEncrypter.encryptId(accountDetail.getAccount().getAccountNo()));
      accountDetail.setEncodedDeviceId(SessionEncrypter.encryptId(accountDetail.getDevice().getId()));
    }
    return this;
  }

  public Overview encodeDeviceId() {
    for (AccountDetail accountDetail : accountDetails) {
      accountDetail.setEncodedDeviceId(SessionEncrypter.encryptId(accountDetail.getDevice().getId()));
    }
    return this;
  }

  public Overview encodeAccountNo() {
    for (AccountDetail accountDetail : accountDetails) {
      accountDetail.setEncodedAccountNum(SessionEncrypter.encryptId(accountDetail.getAccount().getAccountNo()));
    }
    return this;
  }

}