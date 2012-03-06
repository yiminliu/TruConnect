package com.trc.jms.sender;

import com.tscp.mvne.Request;
import com.tscp.mvne.jms.account.AccountJmsProvider;
import com.tscp.mvne.jms.account.AccountRequestSender;

public abstract class TestSender {
  private static final AccountRequestSender sender = AccountJmsProvider.getRequestSenderInstance();

  public void send(Request request) {
    sender.send(request);
  }
}
