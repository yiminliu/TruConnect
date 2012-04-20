package com.trc.util.cache;

import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustTopUp;
import com.tscp.mvne.Device;

public enum CacheKey {
  ACCOUNT, ACCOUNT_DETAIL, ADDRESSES, ALL_ACCOUNTS, CHARGE_HISTORY, CREDIT_CARDS, DEVICE, DEVICES, TOPUP;

  public static final String makeKey(final User user, final CacheKey cacheKey) {
    return makeKey(user, cacheKey, null);
  }

  public static final String makeKey(final User user, final CacheKey cacheKey, final Object obj) {
    if (obj instanceof Account) {
      return makeKey(user, cacheKey, ((Account) obj).getAccountno());
    } else if (obj instanceof Device) {
      return makeKey(user, cacheKey, ((Device) obj).getId());
    } else if (obj instanceof Integer) {
      return makeKey(user, cacheKey, Integer.toString((Integer) obj));
    } else if (obj instanceof String) {
      return makeKey(user, cacheKey, (String) obj);
    } else if (obj instanceof CustTopUp) {
      return makeKey(user, cacheKey, ((CustTopUp) obj).getAccountNo());
    } else {
      return makeKey(user, cacheKey, null);
    }
  }

  public static final String makeKey(final User user, final CacheKey cacheKey, final String modifier) {
    String key = cacheKey != null ? cacheKey.toString() : "";
    key = user != null ? user.getUserId() + key : key;
    key = modifier != null ? key + modifier : key;
    return key;
  }

}