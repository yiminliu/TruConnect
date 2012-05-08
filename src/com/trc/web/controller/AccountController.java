package com.trc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.manager.impl.AccountManager;
import com.trc.manager.impl.UserManager;
import com.trc.security.encryption.SessionEncrypter;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/account")
public class AccountController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;

  @RequestMapping(value = { "", "/", "manage" }, method = RequestMethod.GET)
  public ModelAndView showOverview() {
    ResultModel model = new ResultModel("account/overview");
    User user = userManager.getCurrentUser();
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    model.addObject("accountDetails", overview.getAccountDetails());
    model.addObject("paymentHistory", overview.getPaymentDetails());
    return model.getSuccess();
  }

  @RequestMapping(value = "activity", method = RequestMethod.GET)
  public ModelAndView showActivity() {
    ResultModel model = new ResultModel("account/activity");
    User user = userManager.getCurrentUser();
    List<AccountDetail> accountList = accountManager.getOverview(user).encodeAccountNo().getAccountDetails();
    AccountDetail firstAccount = accountList.size() > 0 ? accountList.get(0) : new AccountDetail();
    model.addObject("accountList", accountList);
    model.addObject("accountDetail", firstAccount);
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
    int accountNo = SessionEncrypter.decryptId(encodedAccountNum);
    Overview overview = accountManager.getOverview(user).encodeAccountNo();
    overview.getAccountDetail(accountNo).getUsageHistory().setCurrentPageNum(page);
    model.addObject("accountList", overview.getAccountDetails());
    model.addObject("accountDetail", overview.getAccountDetail(accountNo));
    return model.getSuccess();
  }

}