package com.trc.security.permission.exception;

public class PermissionNotDefinedException extends RuntimeException {
  private static final long serialVersionUID = 5654373678403294802L;

  public PermissionNotDefinedException() {
    super();
  }

  public PermissionNotDefinedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PermissionNotDefinedException(String message) {
    super(message);
  }

  public PermissionNotDefinedException(Throwable cause) {
    super(cause);
  }

}
