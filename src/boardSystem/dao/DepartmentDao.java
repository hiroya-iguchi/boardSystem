package boardSystem.dao;

import static boardSystem.utils.BoardCloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import boardSystem.beans.Department;
import boardSystem.exception.SQLRuntimeException;

public class DepartmentDao {

	public List<Department> getDepartments(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM depertments";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Department> departmentList = toDepartmentList(rs);
			if (departmentList.isEmpty()) {
				return null;
			} else {
				return  departmentList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Department> toDepartmentList(ResultSet rs) throws SQLException {

		List<Department> ret = new ArrayList<Department>();

		try {

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Department branch = new Department();

				branch.setId(id);
				branch.setName(name);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
