package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class TicketManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public TicketManagementException() {
    super();
  }

  public TicketManagementException(String message) {
    super(message);
  }

  public TicketManagementException(Throwable cause) {
    super(cause);
  }

  public TicketManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
