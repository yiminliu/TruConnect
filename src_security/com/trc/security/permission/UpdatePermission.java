package com.trc.security.permission;

import org.springframework.security.core.Authentication;

import com.trc.config.Config;
import com.trc.user.User;
import com.trc.user.authority.ROLE;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;

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

  public boolean isAllowed(Authentication authentication) {
    boolean hasPermission = false;
    if (isAuthenticated(authentication)) {
      User user = (User) authentication.getPrincipal();
      hasPermission = !Config.ADMIN || isRoleGrantedPermission(user);
    }
    return hasPermission;
  }

  @Override
  public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
    boolean hasPermission = false;
    if (isAuthenticated(authentication)) {
      User user = (User) authentication.getPrincipal();
      if (Config.ADMIN) {
        hasPermission = isRoleGrantedPermission(user);
      } else if (isSelf(user, targetDomainObject)) {
        hasPermission = true;
      } else if (targetDomainObject instanceof Account) {
        DevLogger.log("instance of account, user email {} account email {}", user.getEmail(), ((Account) targetDomainObject).getContactEmail());
        hasPermission = user.getEmail().equals(((Account) targetDomainObject).getContactEmail());
      }
    }
    return hasPermission;
  }

  private boolean isSelf(User user, Object targetDomainObject) {
    return targetDomainObject instanceof User && ((User) targetDomainObject).getUserId() == user.getUserId();
  }
}