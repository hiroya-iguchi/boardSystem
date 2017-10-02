package boardSystem.service;

import static boardSystem.utils.BoardCloseableUtil.*;
import static boardSystem.utils.BoardDBUtil.*;

import java.sql.Connection;
import java.util.List;

import boardSystem.beans.BoardMessage;
import boardSystem.beans.BoardUserMessage;
import boardSystem.beans.Comments;
import boardSystem.dao.BoardMessageDao;
import boardSystem.dao.BoardUserMessageDao;

public class BoardMessageService {

	public void register(BoardMessage message) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardMessageDao messageDao = new BoardMessageDao();
			messageDao.insert(connection, message);

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

	public void newComment(Comments comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardMessageDao messageDao = new BoardMessageDao();
			messageDao.comment(connection, comment);

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

	public void delete(BoardMessage message) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardMessageDao messageDao = new BoardMessageDao();
			messageDao.delete(connection, message);

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

	public void commentDelete(BoardMessage message) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardMessageDao messageDao = new BoardMessageDao();
			messageDao.commentDelete(connection, message);

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

	private static final int LIMIT_NUM = 1000;

	public List<BoardUserMessage> getMessage(String startDate, String endDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserMessageDao messageDao = new BoardUserMessageDao();
			List<BoardUserMessage> ret = messageDao.getUserMessages(connection, LIMIT_NUM , startDate , endDate);

			commit(connection);

			return ret;
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

	public List<Comments> getComment() {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserMessageDao messageDao = new BoardUserMessageDao();
			List<Comments> ret = messageDao.getUserComments(connection, LIMIT_NUM);

			commit(connection);

			return ret;
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

	public List<BoardUserMessage> refine(String category) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserMessageDao MessageDao = new BoardUserMessageDao();
			List<BoardUserMessage> refines = MessageDao.getRefine(connection,category);

			commit(connection);
			return refines;
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
