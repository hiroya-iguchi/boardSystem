package boardSystem.dao;

import static boardSystem.utils.BoardCloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import boardSystem.beans.BoardUserMessage;
import boardSystem.beans.Comments;
import boardSystem.exception.BoardSQLRuntimeException;

public class BoardUserMessageDao {

	public List<BoardUserMessage> getUserMessages(Connection connection, int num, String startDate , String endDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages WHERE ? < created_at  and created_at <? ");
			sql.append("ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			ResultSet rs = ps.executeQuery();
			List<BoardUserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<BoardUserMessage> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<BoardUserMessage> ret = new ArrayList<BoardUserMessage>();
		try {
			while (rs.next()) {
				String title = rs.getString("title");
				String category = rs.getString("category");
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("created_at");

				BoardUserMessage message = new BoardUserMessage();
				message.setTitle(title);
				message.setId(id);
				message.setCategory(category);
				message.setUserId(userId);
				message.setText(text);
				message.setInsertDate(insertDate);

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
			throw new BoardSQLRuntimeException(e);
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

	public List<BoardUserMessage> getRefine(Connection connection,String category) {


		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages ");

			sql.append(" WHERE");
			sql.append(" category = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, category);


			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			List<BoardUserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	private List<BoardUserMessage> toRefineList(ResultSet rs)
			throws SQLException {

		List<BoardUserMessage> ret = new ArrayList<BoardUserMessage>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("message_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("created_at");

				BoardUserMessage message = new BoardUserMessage();
				message.setId(id);
				message.setUserId(userId);
				message.setMessageId(messageId);
				message.setText(text);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
