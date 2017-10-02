package boardSystem.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import boardSystem.exception.BoardIORuntimeException;
import boardSystem.exception.BoardSQLRuntimeException;

public class BoardCloseableUtil {

	public static void close(Closeable closeable) {

		if (closeable == null) {
			return;
		}

		try {
			closeable.close();
		} catch (IOException e) {
			throw new BoardIORuntimeException(e);
		}
	}

	public static void close(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		}
	}

	public static void close(Statement statement) {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		}
	}

	public static void close(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		}
	}
}
