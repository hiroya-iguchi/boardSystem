package boardSystem.service;

import static boardSystem.utils.BoardCloseableUtil.*;
import static boardSystem.utils.BoardDBUtil.*;

import java.sql.Connection;

import boardSystem.beans.User;
import boardSystem.dao.UserDao;
import boardSystem.utils.BoardCipherUtil;

public class LoginService {

	public User login(String loginID, String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = BoardCipherUtil.encrypt(password);
			User user = userDao
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
