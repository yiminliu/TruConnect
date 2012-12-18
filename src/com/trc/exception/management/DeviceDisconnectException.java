package com.trc.exception.management;

public class DeviceDisconnectException extends DeviceManagementException {
	private static final long serialVersionUID = -7019593551529001443L;

	public DeviceDisconnectException() {
		super();
	}

	public DeviceDisconnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceDisconnectException(String message) {
		super(message);
	}

	public DeviceDisconnectException(Throwable cause) {
		super(cause);
	}

}