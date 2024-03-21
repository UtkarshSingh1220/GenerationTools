package com.karkinos.mdm.admin.exception;

import com.karkinos.logging.KarkinosException;

import lombok.Getter;

@Getter
public class VitalMarkerException extends KarkinosException {

	private static final long serialVersionUID = -1705920564637L;
	private final long errorCode;
	private final String[] params;

	public VitalMarkerException(long errorCode, String errorMessage, String... params) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.params = params;
	}

	public VitalMarkerException(long errorCode, Exception exception, String... params) {
		super(exception);
		this.errorCode = errorCode;
		this.params = params;
	}
}
