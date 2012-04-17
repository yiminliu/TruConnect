package com.trc.web.session;

public class SessionTokenInvalidException extends RuntimeException {
  private static final long serialVersionUID = -7774464424045171601L;

  public SessionTokenInvalidException() {
    super();
  }

  public SessionTokenInvalidException(String message, Throwable cause) {
    super(message, cause);
  }

  public SessionTokenInvalidException(String message) {
    super(message);
  }

  public SessionTokenInvalidException(Throwable cause) {
    super(cause);
  }

}
