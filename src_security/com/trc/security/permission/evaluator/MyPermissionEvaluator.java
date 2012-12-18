package com.trc.security.permission.evaluator;

import java.io.Serializable;

import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.security.core.Authentication;

public interface MyPermissionEvaluator extends AopInfrastructureBean {

  boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission);

  boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission);
  
}