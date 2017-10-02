package boardSystem.service;

import static boardSystem.utils.BoardCloseableUtil.*;
import static boardSystem.utils.BoardDBUtil.*;

import java.sql.Connection;

import boardSystem.beans.BoardUser;
import boardSystem.dao.BoardUserDao;

public class AccountService {

	public void account(BoardUser user) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			userDao.account(connection, user);

			commit(connection);
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
