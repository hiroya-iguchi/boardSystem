package boardSystem.exception;


import java.sql.SQLException;

public class BoardSQLRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoardSQLRuntimeException(SQLException cause) {
		super(cause);
	}

}

