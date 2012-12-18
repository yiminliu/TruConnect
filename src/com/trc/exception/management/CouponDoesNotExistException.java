package com.trc.exception.management;

public class CouponDoesNotExistException extends CouponManagementException {
	private static final long serialVersionUID = 1021858917177528391L;

	public CouponDoesNotExistException() {
		super();
	}

	public CouponDoesNotExistException(String message) {
		super(message);
	}

	public CouponDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public CouponDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
