package boardSystem.service;


import static boardSystem.utils.CloseableUtil.*;
import static boardSystem.utils.DBUtil.*;

import java.sql.Connection;

import boardSystem.beans.Comments;
import boardSystem.dao.CommentDao;

public class CommentService {

	public void newComment(Comments comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.comment(connection, comment);

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

	public void commentDelete(Comments comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao CommentDao = new CommentDao();
			CommentDao.commentDelete(connection, comment);

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
