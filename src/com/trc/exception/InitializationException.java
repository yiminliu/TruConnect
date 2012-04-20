package com.trc.exception;

public class InitializationException extends RuntimeException {
  private static final long serialVersionUID = -324523750418296097L;

  public InitializationException() {
    super();
  }

  public InitializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public InitializationException(String message) {
    super(message);
  }

  public InitializationException(Throwable cause) {
    super(cause);
  }

}