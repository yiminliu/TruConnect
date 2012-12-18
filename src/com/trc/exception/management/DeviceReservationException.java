package com.trc.exception.management;

public class DeviceReservationException extends DeviceManagementException {
	private static final long serialVersionUID = -5753587376483009118L;

	public DeviceReservationException() {
		super();
	}

	public DeviceReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceReservationException(String message) {
		super(message);
	}

	public DeviceReservationException(Throwable cause) {
		super(cause);
	}

}
