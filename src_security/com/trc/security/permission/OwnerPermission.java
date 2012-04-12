package com.trc.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.trc.user.User;

@Component
public class OwnerPermission extends Permission {

  @Override
  public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
    boolean hasPermission = false;
    if (isAuthenticated(authentication) && isUserOwnedObject(targetDomainObject)) {
      User user = (User) authentication.getPrincipal();
      if (user.getUserId() == ((UserOwnedObject) targetDomainObject).getOwnerId()) {
        hasPermission = true;
      }
    }
    return hasPermission;
  }

  private boolean isUserOwnedObject(Object targetDomainObject) {
    return targetDomainObject instanceof UserOwnedObject && ((UserOwnedObject) targetDomainObject).getOwnerId() > 0;
  }

}