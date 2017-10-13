package boardSystem.service;

import static boardSystem.utils.CloseableUtil.*;
import static boardSystem.utils.DBUtil.*;

import java.sql.Connection;

import boardSystem.beans.User;
import boardSystem.dao.UserDao;

public class AccountService {

	public void account(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
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
