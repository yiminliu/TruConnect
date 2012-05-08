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

  protected boolean canHandle(Authentication authentication, Object targetDomainObject, Object permission) {
    // return targetDomainObject != null && authentication != null && permission
    // instanceof String;
    return authentication != null && permission instanceof String;
  }

  protected boolean checkPermission(Authentication authentication, Object targetDomainObject, String permissionKey) {
    verifyPermissionIsDefined(permissionKey);
    Permission permission = permissionNameToPermissionMap.get(permissionKey);
    return targetDomainObject == null ? permission.isAllowed(authentication) : permission.isAllowed(authentication, targetDomainObject);
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

  @Override
  @Transactional
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    // TODO
    throw new PermissionNotDefinedException("Id and Class permissions are not supported by " + this.getClass().toString());
  }

  protected void verifyPermissionIsDefined(String permissionKey) throws PermissionNotDefinedException {
    if (!permissionNameToPermissionMap.containsKey(permissionKey)) {
      throw new PermissionNotDefinedException("No permission with key " + permissionKey + " is defined in " + this.getClass().toString());
    }
  }
}
