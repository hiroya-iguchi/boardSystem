package boardSystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardSystem.beans.Message;
import boardSystem.beans.User;
import boardSystem.service.MessageService;

@WebServlet(urlPatterns = { "/commentDelete" })

public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

			User user = (User) session.getAttribute("loginUser");

			Message message = new Message();
			message.setUserId(user.getId());
			System.out.println(request.getParameter("id"));
			message.setId(Integer.parseInt(request.getParameter("id")));

			new MessageService().commentDelete(message);

			response.sendRedirect("./");


	}



}

