package boardSystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardSystem.beans.Comments;
import boardSystem.beans.User;
import boardSystem.service.CommentService;

@WebServlet(urlPatterns = { "/commentDelete" })

public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

			User user = (User) session.getAttribute("loginUser");

			Comments comment = new Comments();
			comment.setUserId(user.getId());
			comment.setId(Integer.parseInt(request.getParameter("id")));

			new CommentService().commentDelete(comment);

			response.sendRedirect("./");


	}



}

