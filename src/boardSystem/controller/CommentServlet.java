package boardSystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import boardSystem.beans.User;
import boardSystem.beans.Comments;
import boardSystem.service.MessageService;

@WebServlet(urlPatterns = { "/comment" })

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> comments = new ArrayList<String>();

		if (isValid(request, comments) == true) {
			User user = (User) session.getAttribute("loginUser");

			Comments comment = new Comments();
			comment.setText(request.getParameter("text"));
			comment.setBranchId(user.getBranchId());
			comment.setDepartmentId(user.getDepartmentId());
			comment.setUserId(user.getId());
			comment.setMessageId(Integer.parseInt(request.getParameter("message_id")));

			new MessageService().newComment(comment);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorComments", comments);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> comments) {

		String text = request.getParameter("text");

		if(text.matches("^[ 　¥t¥n¥x0B¥f¥r]+$") || StringUtils.isEmpty(text) == true ){
			comments.add("コメントを入力してください");
		}

		if(!(text.length() <= 500)){
			comments.add("コメントは500文字以内で入力してください");
		}

		if (comments.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
