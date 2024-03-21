package {{ROOT_PACKAGE}}.exception;

import com.karkinos.logging.KarkinosException;

import lombok.Getter;

@Getter
public class {{VONAME}}NotFoundException extends KarkinosException {

	private static final long serialVersionUID = -{{SERIAL_VERSION_ID}}L;
	private final long errorCode;
	private final String[] params;

	public {{VONAME}}NotFoundException(long errorCode, String errorMsg, String... params) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.params = params;
	}

	public {{VONAME}}NotFoundException(long errorCode, Exception exception, String... params) {
		super(exception);
		this.errorCode = errorCode;
		this.params = params;
	}
}
