package boardSystem.service;

import static boardSystem.utils.BoardCloseableUtil.*;
import static boardSystem.utils.BoardDBUtil.*;

import java.sql.Connection;

import boardSystem.beans.BoardUser;
import boardSystem.dao.BoardUserDao;
import boardSystem.utils.BoardCipherUtil;

public class BoardLoginService {

	public BoardUser login(String loginID, String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			String encPassword = BoardCipherUtil.encrypt(password);
			BoardUser user = userDao
					.getUser(connection, loginID, encPassword);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
