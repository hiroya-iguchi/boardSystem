package boardSystem.dao;

import static boardSystem.utils.BoardCloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import boardSystem.beans.BoardMessage;
import boardSystem.beans.Comments;
import boardSystem.exception.BoardSQLRuntimeException;

public class BoardMessageDao {

	public void insert(Connection connection, BoardMessage message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append(" title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", user_id");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", created_at");
			sql.append(") VALUES (");
			sql.append(" ?"); // user_id
			sql.append(", ?");// text
			sql.append(", ?");// category
			sql.append(", ?");// user_id
			sql.append(", ?");// branch_id
			sql.append(", ?");// department_id
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getTitle());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getUserId());
			ps.setInt(5, message.getBranchId());
			ps.setInt(6, message.getDepartmentId());
			System.out.println(ps);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, BoardMessage message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM messages where id = ? ");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, message.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void comment(Connection connection, Comments comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("text");
			sql.append(", message_id");
			sql.append(", user_id");
			sql.append(", branch");
			sql.append(", department");
			sql.append(", created_at");
			sql.append(") VALUES (");
			sql.append(" ?"); // text
			sql.append(", ?");// user_id
			sql.append(", ?");// user_id
			sql.append(", ?");// branch_id
			sql.append(", ?");// department_id
			sql.append(", CURRENT_TIMESTAMP"); // create_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, comment.getText());
			ps.setInt(2, comment.getMessageId());
			ps.setInt(3, comment.getUserId());
			ps.setInt(4, comment.getBranchId());
			ps.setInt(5, comment.getDepartmentId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void commentDelete(Connection connection, BoardMessage message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments where id = ? ");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, message.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new BoardSQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}




