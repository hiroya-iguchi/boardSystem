package boardSystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardSystem.beans.User;
import boardSystem.service.AccountService;

@WebServlet(urlPatterns = { "/account" })

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

			User user = (User) session.getAttribute("loginUser");
			User working = new User();
			working.setUserId(user.getId());
			working.setId(Integer.parseInt(request.getParameter("id")));
			working.setIsWorking(Integer.parseInt(request.getParameter("working")));

			new AccountService().account(working);

			response.sendRedirect("management");


	}



}
