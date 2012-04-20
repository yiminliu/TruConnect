package com.trc.security.permission;

import org.springframework.security.core.Authentication;

import com.trc.user.User;
import com.trc.user.authority.ROLE;

public class RefundPermission extends Permission {

  public void init() {
    roleRepository.add(ROLE.ROLE_SUPERUSER);
    roleRepository.add(ROLE.ROLE_ADMIN);
    roleRepository.add(ROLE.ROLE_MANAGER);
  }

  @Override
  public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
    boolean hasPermission = false;
    if (isAuthenticated(authentication)) {
      User user = (User) authentication.getPrincipal();
      hasPermission = isRoleGrantedPermission(user);
    }
    return hasPermission;
  }

}
