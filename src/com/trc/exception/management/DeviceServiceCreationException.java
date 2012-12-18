package com.trc.exception.management;

public class DeviceServiceCreationException extends DeviceManagementException {
	private static final long serialVersionUID = 452182347566937423L;

	public DeviceServiceCreationException() {
		super();
	}

	public DeviceServiceCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceServiceCreationException(String message) {
		super(message);
	}

	public DeviceServiceCreationException(Throwable cause) {
		super(cause);
	}

}
