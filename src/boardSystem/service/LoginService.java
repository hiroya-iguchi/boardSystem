package boardSystem.service;

import static boardSystem.utils.CloseableUtil.*;
import static boardSystem.utils.DBUtil.*;

import java.sql.Connection;

import boardSystem.beans.User;
import boardSystem.dao.UserDao;
import boardSystem.utils.CipherUtil;

public class LoginService {

	public User login(String loginID, String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
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
