package com.trc.security.permission;

import org.springframework.security.core.Authentication;


public class RefundPermission extends Permission {

  @Override
  public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
    // TODO Auto-generated method stub
    return false;
  }

}
