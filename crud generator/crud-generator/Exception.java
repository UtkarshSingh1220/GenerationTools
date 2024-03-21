package {{ROOT_PACKAGE}}.exception;

import com.karkinos.logging.KarkinosException;

import lombok.Getter;

@Getter
public class {{VONAME}}Exception extends KarkinosException {

	private static final long serialVersionUID = -{{SERIAL_VERSION_ID}}L;
	private final long errorCode;
	private final String[] params;

	public {{VONAME}}Exception(long errorCode, String errorMessage, String... params) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.params = params;
	}

	public {{VONAME}}Exception(long errorCode, Exception exception, String... params) {
		super(exception);
		this.errorCode = errorCode;
		this.params = params;
	}
}
