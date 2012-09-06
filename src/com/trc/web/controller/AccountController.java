package com.trc.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.domain.support.notification.NotificationMessage;
import com.trc.domain.support.notification.NotificationPage;
import com.trc.domain.support.notification.NotificationSchedule;
import com.trc.manager.AccountManager;
import com.trc.manager.NotificationManager;
import com.trc.manager.UserManager;
import com.trc.security.encryption.SessionEncrypter;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.user.account.UsageHistory;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/account")
public class AccountController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;
  @Autowired
  private NotificationManager notificationManager;

  @RequestMapping(value = { "", "/", "manage" }, method = RequestMethod.GET)
  public ModelAndView showOverview() {
    ResultModel model = new ResultModel("account/overview");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    Set<NotificationMessage> notificationMessages = (Set<NotificationMessage>)notificationManager.getSelectededNotificationMessages("account/overview");
    model.addObject("accountDetails", overview.getAccountDetails());
    model.addObject("paymentHistory", overview.getPaymentDetails());
    model.addObject("notificationMessages", notificationMessages);
    return model.getSuccess();
  }

  @RequestMapping(value = "activity", method = RequestMethod.GET)
  public ModelAndView showActivity() {
    ResultModel model = new ResultModel("account/activity");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    int numAccounts = overview.getAccountDetails().size();
    List<AccountDetail> accountList = overview.getAccountDetails();
    List<AccountDetail> firstAccount = numAccounts > 0 ? overview.getAccountDetails().subList(0, 1) : new ArrayList<AccountDetail>();
    model.addObject("numAccounts", numAccounts);
    model.addObject("accountList", accountList);
    model.addObject("accountDetails", firstAccount);
    model.addObject("encodedAccountNumber", firstAccount.get(0).getEncodedAccountNum());
    return model.getSuccess();
  }

  @RequestMapping(value = "activity/{encodedAccountNum}", method = RequestMethod.GET)
  public String showAccountActivity(@PathVariable("encodedAccountNum") String encodedAccountNum) {
    return "redirect:/account/activity/" + encodedAccountNum + "/1";
  }

  @RequestMapping(value = "activity/{encodedAccountNum}/{page}", method = RequestMethod.GET)
  public ModelAndView showAccountActivity(@PathVariable("encodedAccountNum") String encodedAccountNum, @PathVariable("page") int page) {
    ResultModel model = new ResultModel("account/activity");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    int numAccounts = overview.getAccountDetails().size();
    int accountNum = SessionEncrypter.decryptId(encodedAccountNum);
    List<AccountDetail> accountList = overview.getAccountDetails();
    overview.getAccountDetail(accountNum).getUsageHistory().setCurrentPageNum(page);
    
    List<AccountDetail> accountDetails = new ArrayList<AccountDetail>();
    accountDetails.add(overview.getAccountDetail(accountNum));
    model.addObject("numAccounts", numAccounts);
    model.addObject("accountList", accountList);
    model.addObject("accountDetails", accountDetails);
    model.addObject("encodedAccountNumber", encodedAccountNum);
    return model.getSuccess();
  }

}