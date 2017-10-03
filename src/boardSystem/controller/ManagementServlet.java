package boardSystem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardSystem.beans.User;
import boardSystem.service.UserService;

@WebServlet(urlPatterns = { "/management" })
public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		List<User> users = new UserService().getAllUser();
		for (User s : users) {
			s.setBranchName(new UserService().getSelectBranch(s.getBranchId()));
			s.setDepartmentName(new UserService().getSelectDepartment(s.getDepartmentId()));
		}
		request.setAttribute("users", users);


//		List<Branch> branches = new BoardUserService().getAllBranch();
//		request.setAttribute("branches", branches);
//
//		List<Department> departments = new BoardUserService().getAllDepartment();
//		request.setAttribute("departments", departments);

		request.getRequestDispatcher("management.jsp").forward(request, response);
	}
}