package com.trc.security.permission.exception;

public class PermissionNotGrantedException extends RuntimeException {
  private static final long serialVersionUID = -3661168053096977906L;

  public PermissionNotGrantedException() {
    super();
  }

  public PermissionNotGrantedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PermissionNotGrantedException(String message) {
    super(message);
  }

  public PermissionNotGrantedException(Throwable cause) {
    super(cause);
  }

}
