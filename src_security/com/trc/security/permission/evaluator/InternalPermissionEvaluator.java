package com.trc.security.permission.evaluator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.trc.security.permission.Permission;
import com.trc.security.permission.exception.PermissionNotDefinedException;

public class InternalPermissionEvaluator implements PermissionEvaluator {
  private Map<String, Permission> permissionNameToPermissionMap = new HashMap<String, Permission>();

  protected InternalPermissionEvaluator() {
    // prevent instantiation
  }

  public InternalPermissionEvaluator(Map<String, Permission> permissionNameToPermissionMap) {
    this.permissionNameToPermissionMap = permissionNameToPermissionMap;
  }

  @Override
  @Transactional
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    boolean hasPermission = false;
    if (canHandle(authentication, targetDomainObject, permission)) {
      hasPermission = checkPermission(authentication, targetDomainObject, (String) permission);
    }
    return hasPermission;
  }

  private boolean canHandle(Authentication authentication, Object targetDomainObject, Object permission) {
    return targetDomainObject != null && authentication != null && permission instanceof String;
  }

  private boolean checkPermission(Authentication authentication, Object targetDomainObject, String permissionKey) {
    verifyPermissionIsDefined(permissionKey);
    Permission permission = permissionNameToPermissionMap.get(permissionKey);
    return permission.isAllowed(authentication, targetDomainObject);
  }

  private void verifyPermissionIsDefined(String permissionKey) throws PermissionNotDefinedException {
    if (!permissionNameToPermissionMap.containsKey(permissionKey)) {
      throw new PermissionNotDefinedException("No permission with key " + permissionKey + " is defined in " + this.getClass().toString());
    }
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    // not yet implemented
    throw new PermissionNotDefinedException("Id and Class permissions are not supported by " + this.getClass().toString());
  }
}
