package com.trc.security.permission;

import org.springframework.security.core.Authentication;

import com.trc.user.User;
import com.trc.user.authority.ROLE;

/**
 * This was created as a test permission during development and is not
 * finalized. Ideally objects will be linked back to their owners and this class
 * would extend OwnerPermission (also unfinished). Currently this only checks if
 * the User is updating himself, or if he has a "admin" authority.
 * 
 * @author Tachikoma
 * 
 */
public class UpdatePermission extends Permission {

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
      if (isSelf(user, targetDomainObject)) {
        hasPermission = true;
      } else {
        hasPermission = isRoleGrantedPermission(user);
      }
    }
    return hasPermission;
  }

  private boolean isSelf(User user, Object targetDomainObject) {
    return targetDomainObject instanceof User && ((User) targetDomainObject).getUserId() == user.getUserId();
  }
}