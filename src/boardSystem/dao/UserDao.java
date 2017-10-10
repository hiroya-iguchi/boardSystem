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

import boardSystem.beans.Branch;
import boardSystem.beans.Department;
import boardSystem.beans.User;
import boardSystem.exception.NoRowsUpdatedRuntimeException;
import boardSystem.exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String loginID,
			String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginID);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();

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


				User user = new User();
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

	public void insert(Connection connection, User user) {

		PreparedStatement ps_branchs = null;
		PreparedStatement ps_departments = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			//branch_idの取得
			int branch_id = 0;
			String select_branchs = "SELECT id FROM branches WHERE name = ?";
			ps_branchs = connection.prepareStatement(select_branchs);
			ps_branchs.setString(1, user.getBranchName());
			rs = ps_branchs.executeQuery();
			while(rs.next()){
				branch_id = rs.getInt("id");
			}

			//department_idの取得
			int department_id = 0;
			String select_departments = "SELECT id FROM depertments WHERE name = ?";
			ps_departments = connection.prepareStatement(select_departments);
			ps_departments.setString(1, user.getDepartmentName());
			rs = ps_departments.executeQuery();
			while(rs.next()){
				department_id = rs.getInt("id");
			}

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
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User user) {

		PreparedStatement ps_branchs = null;
		PreparedStatement ps_departments = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			//branch_idの取得
			int branch_id = 0;
			String select_branchs = "SELECT id FROM branches WHERE name = ?";
			ps_branchs = connection.prepareStatement(select_branchs);
			ps_branchs.setString(1, user.getBranchName());
			rs = ps_branchs.executeQuery();
			while(rs.next()){
				branch_id = rs.getInt("id");
			}

			//department_idの取得
			int department_id = 0;
			String select_departments = "SELECT id FROM depertments WHERE name = ?";
			ps_departments = connection.prepareStatement(select_departments);
			ps_departments.setString(1, user.getDepartmentName());
			rs = ps_departments.executeQuery();
			while(rs.next()){
				department_id = rs.getInt("id");
			}

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");

			if(!StringUtils.isEmpty(user.getPassword())){
				sql.append(", password = ?");
			}
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			System.out.println(user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getDepartmentId());
			if(!StringUtils.isEmpty(user.getPassword())){
				ps.setString(5, user.getPassword());
				ps.setInt(6, user.getId());
			}else{
				ps.setInt(5, user.getId());
			}
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public List<User> getAllUser(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return  userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
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
			throw new SQLRuntimeException(e);
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
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void account(Connection connection, User user) {

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
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User checkId(Connection connection, User user ) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? ";

			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getLoginId());
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getUser(Connection connection,String loginId) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? ";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.size() == 1) {
				//リストにすでにユーザーがいれば中身を返す
				return userList.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User isId(Connection connection, User user ) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ? ";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.size() == 1) {
				//リストにすでにユーザーがいれば中身を返す
				return userList.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}

