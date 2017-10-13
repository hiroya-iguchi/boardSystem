package boardSystem.dao;

import static boardSystem.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import boardSystem.beans.Comments;
import boardSystem.exception.SQLRuntimeException;

public class CommentDao {

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
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void commentDelete(Connection connection, Comments comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments where id = ? ");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, comment.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
