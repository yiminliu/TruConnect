package com.trc.exception.management;

public class DeviceActivationException extends DeviceManagementException {
	private static final long serialVersionUID = 7862212766227582407L;

	public DeviceActivationException() {
		super();
	}

	public DeviceActivationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceActivationException(String message) {
		super(message);
	}

	public DeviceActivationException(Throwable cause) {
		super(cause);
	}

}
