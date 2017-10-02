package boardSystem.exception;

import java.io.IOException;

public class BoardIORuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoardIORuntimeException(IOException cause) {
		super(cause);
	}

}
