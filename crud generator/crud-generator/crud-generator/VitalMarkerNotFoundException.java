package com.karkinos.mdm.admin.exception;

import com.karkinos.logging.KarkinosException;

import lombok.Getter;

@Getter
public class VitalMarkerNotFoundException extends KarkinosException {

	private static final long serialVersionUID = -1705920564637L;
	private final long errorCode;
	private final String[] params;

	public VitalMarkerNotFoundException(long errorCode, String errorMsg, String... params) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.params = params;
	}

	public VitalMarkerNotFoundException(long errorCode, Exception exception, String... params) {
		super(exception);
		this.errorCode = errorCode;
		this.params = params;
	}
}
