package com.trc.security.permission;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trc.user.User;
import com.trc.user.authority.Authority;
import com.trc.user.authority.ROLE;

public abstract class Permission {
  protected final Set<ROLE> roleRepository = new HashSet<ROLE>();

  public Collection<GrantedAuthority> getAuthorities(Authentication authentication) {
    return ((UserDetails) authentication.getPrincipal()).getAuthorities();
  }

  public String getLogin(Authentication authentication) {
    return ((UserDetails) authentication.getPrincipal()).getUsername();
  }

  public boolean isAllowed(Authentication authentication) {
    boolean hasPermission = false;
    if (isAuthenticated(authentication)) {
      User user = (User) authentication.getPrincipal();
      hasPermission = isRoleGrantedPermission(user);
    }
    return hasPermission;
  }

  public abstract boolean isAllowed(Authentication authentication, Object targetDomainObject);

  public boolean isAuthenticated(Authentication authentication) {
    return authentication != null && authentication.getPrincipal() instanceof UserDetails;
  }

  protected boolean isRoleGrantedPermission(User user) {
    for (Authority authority : user.getRoles()) {
      if (roleRepository.contains(authority.getRole())) {
        return true;
      }
    }
    return false;
  }

}