package boardSystem.service;

import static boardSystem.utils.CloseableUtil.*;
import static boardSystem.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import boardSystem.beans.Department;
import boardSystem.dao.DepartmentDao;

public class DepartmentService {

	public List<Department> getDepartments() {

		Connection connection = null;
		try {
			connection = getConnection();

			DepartmentDao departmentDao = new DepartmentDao();
			List<Department> user =
					departmentDao.getDepartments(connection);

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
