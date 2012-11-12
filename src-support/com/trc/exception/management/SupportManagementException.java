package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class SupportManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public SupportManagementException() {
    super();
  }

  public SupportManagementException(String message) {
    super(message);
  }

  public SupportManagementException(Throwable cause) {
    super(cause);
  }

  public SupportManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
