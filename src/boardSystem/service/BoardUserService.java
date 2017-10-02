package boardSystem.service;

import static boardSystem.utils.BoardCloseableUtil.*;
import static boardSystem.utils.BoardDBUtil.*;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import boardSystem.beans.BoardUser;
import boardSystem.beans.Branch;
import boardSystem.beans.Department;
import boardSystem.dao.BoardUserDao;
import boardSystem.utils.BoardCipherUtil;
public class BoardUserService {

	public void register(BoardUser user) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = BoardCipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);


			BoardUserDao userDao = new BoardUserDao();
			userDao.insert(connection, user);

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



	public void update(BoardUser user) {

		Connection connection = null;
		try {
			connection = getConnection();

			if(!StringUtils.isEmpty(user.getPassword())){
				System.out.println("aaa");
				String encPassword = BoardCipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
			}

			BoardUserDao userDao = new BoardUserDao();
			userDao.update(connection, user );
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

	public BoardUser getUser(int Id) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			BoardUser user = userDao.getUser(connection, Id);

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

	public List<BoardUser> getAllUser() {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			List<BoardUser> user = userDao.getAllUser(connection);

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

	public List<Branch> getAllBranch() {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			List<Branch> user = userDao.getBranch(connection);

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

	public String getSelectBranch(int i) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			String user = userDao.getSelectBranch(connection, i);

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

	public List<Department> getAllDepartment() {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			List<Department> user = userDao.getDepartment(connection);

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

	public String getSelectDepartment(int i) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			String user = userDao.getSelectDepartment(connection,i);

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

	public boolean checkId(BoardUser user) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			BoardUser checkId = userDao.checkId(connection, user);

			commit(connection);
			if(checkId == null){
				return true;
			}else{
				return false;
			}

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


	public boolean checkName(BoardUser user) {

		Connection connection = null;
		try {
			connection = getConnection();

			BoardUserDao userDao = new BoardUserDao();
			BoardUser checkName = userDao.checkId(connection, user);

			commit(connection);
			if(checkName == null){
				return true;
			}else{
				return false;
			}

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
