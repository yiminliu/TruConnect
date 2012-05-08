package com.trc.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.AddressManagementException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.service.AccountServiceException;
import com.trc.manager.AccountManagerModel;
import com.trc.service.impl.AccountService;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.user.account.PaymentHistory;
import com.trc.user.account.UsageHistory;
import com.trc.user.contact.Address;
import com.trc.user.contact.ContactInfo;
import com.trc.util.cache.CacheKey;
import com.trc.util.cache.CacheManager;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustAcctMapDAO;
import com.tscp.mvne.CustInfo;
import com.tscp.mvne.CustTopUp;
import com.tscp.mvne.Device;
import com.tscp.mvne.PaymentRecord;
import com.tscp.mvne.UsageDetail;

@Component
@SuppressWarnings("unchecked")
public class AccountManager implements AccountManagerModel {
  @Autowired
  private AccountService accountService;
  @Autowired
  private AddressManager addressManager;
  @Autowired
  private DeviceManager deviceManager;
  @Autowired
  private UserManager userManager;

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Account createShellAccount(User user) throws AccountManagementException, AddressManagementException {
    try {
      Account account = accountService.createShellAccount(user);
      return account;
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public CustInfo getCustInfo(User user) throws AccountManagementException {
    try {
      return accountService.getCustInfo(user);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public AccountDetail getAccountDetail(User user, Device deviceInfo) throws AccountManagementException {
    int accountNumber = deviceInfo.getAccountNo();
    try {
      Account account = getAccount(accountNumber);
      AccountDetail accountDetail = new AccountDetail();
      accountDetail.setDevice(deviceInfo);
      accountDetail.setAccount(account);
      accountDetail.setTopUp(getTopUp(user, account).getTopupAmount());
      accountDetail.setUsageHistory(new UsageHistory(getChargeHistory(user, account.getAccountNo()), user, account.getAccountNo()));
      return accountDetail;
    } catch (AccountManagementException e) {
      throw e;
    }
  }

  public AccountDetail getAccountDetail(User user, int deviceId) throws AccountManagementException {
    try {
      return getAccountDetail(user, deviceManager.getDeviceInfo(user, deviceId));
    } catch (DeviceManagementException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<AccountDetail> getAccountDetailList(User user) throws AccountManagementException {
    List<Device> deviceList;

    try {
      deviceList = deviceManager.getDeviceInfoList(user);
    } catch (DeviceManagementException e) {
      deviceList = new ArrayList<Device>();
    }

    List<AccountDetail> accountDetailList = new ArrayList<AccountDetail>();
    AccountDetail accountDetail;
    try {
      for (Device deviceInfo : deviceList) {
        accountDetail = getAccountDetail(user, deviceInfo);
        accountDetailList.add(accountDetail);
      }
    } catch (AccountManagementException e) {
      throw e;
    }
    return accountDetailList;
  }

  private List<Account> getAccountListFromCache(User user) {
    // return (List<Account>) CacheManager.get(CacheKey.ACCOUNTS);
    return (List<Account>) CacheManager.get(user, CacheKey.ALL_ACCOUNTS);
  }

  private Account getAccountFromCache(int accountNumber) {
    List<Account> accountList = getAccountListFromCache(userManager.getCurrentUser());
    Account cachedAccount = (Account) CacheManager.get(userManager.getCurrentUser(), CacheKey.ACCOUNT, Integer.toString(accountNumber));
    if (cachedAccount != null) {
      return cachedAccount;
    } else if (accountList != null) {
      for (Account account : accountList) {
        if (account.getAccountNo() == accountNumber) {
          return account;
        }
      }
    }
    return null;
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Account getAccount(int accountNumber) throws AccountManagementException {
    Account account = getAccountFromCache(accountNumber);
    if (account != null) {
      return account;
    } else {
      try {
        Account fetchedAccount = accountService.getAccount(accountNumber);
        CacheManager.set(userManager.getCurrentUser(), CacheKey.ACCOUNT, fetchedAccount);
        return fetchedAccount;
      } catch (AccountServiceException e) {
        throw new AccountManagementException(e.getMessage(), e.getCause());
      }
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<CustAcctMapDAO> getAccountMap(User user) throws AccountManagementException {
    try {
      return accountService.getAccountMap(user);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<Account> getAccounts(User user) throws AccountManagementException {
    List<Account> accountList = getAccountListFromCache(user);
    if (accountList != null) {
      return accountList;
    } else {
      try {
        accountList = new ArrayList<Account>();
        for (CustAcctMapDAO accountMap : getAccountMap(user)) {
          accountList.add(getAccount(accountMap.getAccountNo()));
        }
        // CacheManager.set(CacheKey.ACCOUNTS, accountList);
        CacheManager.set(user, CacheKey.ALL_ACCOUNTS, accountList);
        return accountList;
      } catch (AccountManagementException e) {
        throw e;
      }
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<UsageDetail> getChargeHistory(User user, int accountNumber) throws AccountManagementException {
    List<UsageDetail> chargeHistory = (List<UsageDetail>) CacheManager.get(user, CacheKey.CHARGE_HISTORY, accountNumber);
    if (chargeHistory != null) {
      return chargeHistory;
    } else {
      try {
        chargeHistory = accountService.getChargeHistory(user, accountNumber);
        String key = CacheKey.makeKey(user, CacheKey.CHARGE_HISTORY, accountNumber);
        CacheManager.set(key, chargeHistory);
        return chargeHistory;
      } catch (AccountServiceException e) {
        throw new AccountManagementException(e.getMessage(), e.getCause());
      }
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public ContactInfo getDefaultContactInfo(User user) throws AccountManagementException, AddressManagementException {
    List<Account> accountList;
    try {
      accountList = getAccounts(user);
      Account account = accountList.get(0);
      Address address = addressManager.getDefaultAddress(user);
      ContactInfo contactInfo = new ContactInfo();
      contactInfo.setAddress(address);
      contactInfo.setFirstName(account.getFirstname());
      contactInfo.setLastName(account.getLastname());
      contactInfo.setPhoneNumber(account.getContactNumber());
      return contactInfo;
    } catch (AccountManagementException e) {
      throw e;
    } catch (AddressManagementException e) {
      throw e;
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public XMLGregorianCalendar getLastAccessFeeDate(User user, Account account) {
    XMLGregorianCalendar accessFeeDate = null;
    List<UsageDetail> usageDetails;
    try {
      usageDetails = getChargeHistory(user, account.getAccountNo());
    } catch (AccountManagementException e) {
      usageDetails = new ArrayList<UsageDetail>();
    }
    for (UsageDetail usageDetail : usageDetails) {
      if (accessFeeDate == null && usageDetail.getUsageType().equals("Access Fee")) {
        accessFeeDate = usageDetail.getEndTime();
        break;
      }
    }
    return accessFeeDate;
  }

  @Loggable(value = LogLevel.TRACE)
  public int getNumAccounts(User user) {
    try {
      List<CustAcctMapDAO> accounts = getAccountMap(user);
      return accounts != null ? accounts.size() : 0;
    } catch (AccountManagementException e) {
      return 0;
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public Overview getOverview(User user) {
    List<Device> deviceInfoList;
    try {
      deviceInfoList = deviceManager.getDeviceInfoList(user);
    } catch (DeviceManagementException e) {
      deviceInfoList = new ArrayList<Device>();
    }
    return new Overview(this, deviceInfoList, user);
  }

  @Loggable(value = LogLevel.TRACE)
  public PaymentHistory getPaymentHistory(User user) throws AccountManagementException {
    try {
      return new PaymentHistory(getPaymentRecords(user), user);
    } catch (AccountManagementException e) {
      throw e;
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<PaymentRecord> getPaymentRecords(User user) throws AccountManagementException {
    try {
      return accountService.getPaymentRecords(user);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public CustTopUp getTopUp(User user, Account account) throws AccountManagementException {
    CustTopUp topup = (CustTopUp) CacheManager.get(user, CacheKey.TOPUP, account);
    if (topup != null) {
      return topup;
    } else {
      try {
        topup = accountService.getTopUp(user, account);
        CacheManager.set(user, CacheKey.TOPUP, topup);
        return topup;
      } catch (AccountServiceException e) {
        throw new AccountManagementException(e.getMessage(), e.getCause());
      }
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public CustTopUp setTopUp(User user, double amount, Account account) throws AccountManagementException {
    try {
      CustTopUp topup = accountService.setTopUp(user, amount, account);
      CacheManager.set(user, CacheKey.TOPUP, topup);
      return topup;
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void updateEmail(Account account) throws AccountManagementException {
    try {
      accountService.updateEmail(account);
      CacheManager.clearCache(userManager.getCurrentUser(), CacheKey.ALL_ACCOUNTS);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  @PreAuthorize("isAuthenticated() and hasPermission(#account, 'canUpdate')")
  public void updateEmail(Account account, String newEmail) throws AccountManagementException {
    try {
      account.setContactEmail(newEmail);
      accountService.updateEmail(account);
      CacheManager.clearCache(userManager.getCurrentUser(), CacheKey.ALL_ACCOUNTS);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

}
