package boardSystem.dao;

import static boardSystem.utils.BoardCloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import boardSystem.beans.Comments;
import boardSystem.beans.UserMessage;
import boardSystem.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, String startDate , String endDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages WHERE ? < created_at  and created_at <? ");
			sql.append("ORDER BY created_at DESC  " );

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				String title = rs.getString("title");
				String category = rs.getString("category");
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("created_at");

				UserMessage message = new UserMessage();
				message.setTitle(title);
				message.setId(id);
				message.setCategory(category);
				message.setUserId(userId);
				message.setText(text);
				message.setInsertDate(insertDate);
				message.setBranchId(branchId);
				message.setDepartmentId(departmentId);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<Comments> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_comments ");
			sql.append("ORDER BY created_at ASC limit " + num);
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Comments> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Comments> toUserCommentList(ResultSet rs)
			throws SQLException {

		List<Comments> ret = new ArrayList<Comments>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("message_id");
				int branchId = rs.getInt("branch");
				int departmentId = rs.getInt("department");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("created_at");

				Comments comment = new Comments();
				comment.setId(id);
				comment.setUserId(userId);
				comment.setMessageId(messageId);
				comment.setText(text);
				comment.setCreateDate(insertDate);
				comment.setBranchId(branchId);
				comment.setDepartmentId(departmentId);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<UserMessage> categorize(Connection connection,String category) {


		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages ");

			sql.append(" WHERE");
			sql.append(" category = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, category);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public List<UserMessage> startAndDate(Connection connection,String category, String startDate , String endDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages WHERE category = ? AND ? < created_at  and created_at <? ");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, category);
			ps.setString(2, startDate);
			ps.setString(3, endDate);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UserMessage> getUserMessages(Connection connection, int num, String start_date, String end_date, String category) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages where ? <= created_at and created_at <= ?");
			if(category != null){
				sql.append(" and category = ? ");
			}
			sql.append("ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, start_date);
			ps.setString(2, end_date);
			if(category != null){
				ps.setString(3, category);
			}

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);

			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
