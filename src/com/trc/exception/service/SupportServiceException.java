package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class SupportServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public SupportServiceException() {
    super();
  }

  public SupportServiceException(String message) {
    super(message);
  }

  public SupportServiceException(Throwable cause) {
    super(cause);
  }

  public SupportServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
