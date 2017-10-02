package boardSystem.dao;

import static boardSystem.utils.BoardCloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import boardSystem.beans.BoardUser;
import boardSystem.beans.Branch;
import boardSystem.beans.Department;
import boardSystem.exception.BoardNoRowsUpdatedRuntimeException;
import boardSystem.exception.BoardSQLRuntimeException;

public class BoardUserDao {

	public BoardUser getUser(Connection connection, String loginID,
			String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginID);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			List<BoardUser> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<BoardUser> toUserList(ResultSet rs) throws SQLException {

		List<BoardUser> ret = new ArrayList<BoardUser>();

		try {
			//String branchName = null;
			//String departmentName = null;

			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String password = rs.getString("password");
				int working = rs.getInt("is_working");
				Timestamp createDate= rs.getTimestamp("created_at");
				Timestamp updateDate = rs.getTimestamp("updated_at");


				BoardUser user = new BoardUser();
//				コメントアウトを外した処理でも支店名や役職名の表示が可能
//				if(branchId.equals("1")){
//					branchName = "本社";
//				}else if(branchId.equals("2")){
//					branchName = "支店A";
//				}else if(branchId.equals("3")){
//					branchName = "支店B";
//				}else if(branchId.equals("4")){
//					branchName = "支店C";
//				}
//
//				if(departmentId.equals("1")){
//					departmentName = "総務人事担当";
//				}else if(departmentId.equals("2")){
//					departmentName ="情報管理担当";
//				}else if(departmentId.equals("3")){
//					departmentName = "店長";
//				}else if(departmentId.equals("4")){
//					departmentName = "社員";
//				}

				user.setId(id);
				user.setLoginId(loginId);
				user.setName(name);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setPassword(password);
				user.setIsWorking(working);
				user.setCreateDate(createDate);
				user.setUpdateDate(updateDate);
//				user.setBranchName(branchName);
//				user.setDepartmentName(departmentName);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private List<Branch> toBranchList(ResultSet rs) throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();

		try {

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branch branch = new Branch();

				branch.setId(id);
				branch.setName(name);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private List<Department> toDepartmentList(ResultSet rs) throws SQLException {

		List<Department> ret = new ArrayList<Department>();

		try {

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Department department = new Department();

				department.setId(id);
				department.setName(name);

				ret.add(department);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void insert(Connection connection, BoardUser user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");

			sql.append("login_id");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", password");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			//sql.append("NEXT VALUE FOR my_seq "); // id
			sql.append(" ?"); // login_id
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", ?"); // password
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getDepartmentId());
			ps.setString(5, user.getPassword());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, BoardUser user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(", is_working = ?");

			if(!StringUtils.isEmpty(user.getPassword())){
				sql.append(", password = ?");
			}
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getDepartmentId());
			ps.setInt(5, user.getIsWorking());
			ps.setInt(6, user.getId());
			if(!StringUtils.isEmpty(user.getPassword())){
				ps.setString(6, user.getPassword());
				ps.setInt(7, user.getId());
			}else{
				ps.setInt(6, user.getId());
			}
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new BoardNoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public BoardUser getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<BoardUser> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<BoardUser> getAllUser(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<BoardUser> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return  userList;
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Branch> getBranch(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM branches";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Branch> userList = toBranchList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return  userList;
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public String getSelectBranch(Connection connection , int i) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM branches WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, i);

			ResultSet rs = ps.executeQuery();
			String name = null;
			while (rs.next()) {
				 name = rs.getString("name");
			}
				return  name;
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Department> getDepartment(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM depertments";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Department> userList = toDepartmentList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return  userList;
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public String getSelectDepartment(Connection connection , int i) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM depertments WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, i);

			ResultSet rs = ps.executeQuery();
			String name = null;
			while (rs.next()) {
				 name = rs.getString("name");
			}
				return  name;
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void account(Connection connection, BoardUser user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" is_working = ?");

			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, user.getIsWorking());
			ps.setInt(2, user.getId());
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new BoardNoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public BoardUser checkId(Connection connection, BoardUser user ) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? ";

			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getLoginId());
			ResultSet rs = ps.executeQuery();
			List<BoardUser> userList = toUserList(rs);
			if (userList.isEmpty() != true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public BoardUser checkPassword(Connection connection, BoardUser user ) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE password = ? ";

			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getPassword());
			ResultSet rs = ps.executeQuery();
			List<BoardUser> userList = toUserList(rs);
			if (userList.isEmpty() != true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public BoardUser checkName(Connection connection, BoardUser user ) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE name = ? ";

			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getName());
			ResultSet rs = ps.executeQuery();
			List<BoardUser> userList = toUserList(rs);
			if (userList.isEmpty() != true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}

