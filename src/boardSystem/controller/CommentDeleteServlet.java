package boardSystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardSystem.beans.BoardMessage;
import boardSystem.beans.BoardUser;
import boardSystem.service.BoardMessageService;

@WebServlet(urlPatterns = { "/commentDelete" })

public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

			BoardUser user = (BoardUser) session.getAttribute("loginUser");

			BoardMessage message = new BoardMessage();
			message.setUserId(user.getId());
			System.out.println(request.getParameter("id"));
			message.setId(Integer.parseInt(request.getParameter("id")));

			new BoardMessageService().commentDelete(message);

			response.sendRedirect("./");


	}



}

