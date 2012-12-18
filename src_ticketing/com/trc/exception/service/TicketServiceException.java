package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class TicketServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public TicketServiceException() {
    super();
  }

  public TicketServiceException(String message) {
    super(message);
  }

  public TicketServiceException(Throwable cause) {
    super(cause);
  }

  public TicketServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
