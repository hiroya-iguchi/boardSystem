package boardSystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import boardSystem.beans.Branch;
import boardSystem.beans.Department;
import boardSystem.beans.User;
import boardSystem.service.BranchService;
import boardSystem.service.DepartmentService;
import boardSystem.service.UserService;

//URLパターンを指定するアノテーション
@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branches = new BranchService().getBranches();
		List<Department> departments = new DepartmentService().getDepartments();

		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String id = request.getParameter("login_id");
		String name = request.getParameter("name");


		List<String> messages = new ArrayList<String>();
//		HttpSession session = request.getSession();

		if (isValid(request, messages) == true) {

			User user = new User();
			user.setLoginId(request.getParameter("login_id"));
			user.setName(request.getParameter("name"));
			user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
			user.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
			user.setBranchName(request.getParameter("branchName"));
			user.setDepartmentName(request.getParameter("departmentName"));
			user.setPassword(request.getParameter("password"));
			new UserService().register(user);

			response.sendRedirect("management");

		} else {
			List<Branch> branches = new BranchService().getBranches();
			List<Department> departments = new DepartmentService().getDepartments();

			User user = new User();
			user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
			user.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
			request.setAttribute("errorMessages", messages);
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);
			request.setAttribute("login_id", id);
			request.setAttribute("name", name);
			request.setAttribute("user", user);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String checkPassword =request.getParameter("password2");
		int branchId = Integer.parseInt(request.getParameter("branch_id"));
		int departmentId = Integer.parseInt(request.getParameter("department_id"));

		if (isCheckId(request) != null) {
			messages.add("すでに使用されているログインIDです");
		}

		if (StringUtils.isEmpty(id) == true) {
			messages.add("ログインIDを入力してください");
		}else if(!id.matches("^[0-9a-zA-Z]{6,20}$") ){
			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");

		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}else if(!(name.length() <= 10) ){
				messages.add("名前は10文字以下で入力してください");

		}

		if (StringUtils.isEmpty(password) == true || StringUtils.isEmpty(checkPassword) == true) {
			messages.add("パスワードを入力してください");
		}else if(!password.matches("^[\\p{Punct}0-9a-zA-Z]{6,20}$") ){
				messages.add("パスワードは半角英数字（記号含む）6文字以上20文字以下で入力してください");
		}else if(!(password.equals(checkPassword))){
			messages.add("パスワードが一致しません");

		}

		if(branchId == 1 && departmentId == 3 ){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
		}else if(branchId == 1 && departmentId == 4){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
			}

		if(branchId == 2 && departmentId == 1 ){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
		}else if(branchId == 2 && departmentId == 2){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
			}


		if(branchId == 3 && departmentId == 1 ){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
		}else if(branchId == 3 && departmentId == 2){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
			}


		if(branchId == 4 && departmentId == 1 ){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
		}else if(branchId == 4 && departmentId == 2){
			messages.add("支店名と部署・役職の組み合わせが不適切です");
			}


		// TODO アカウントが既に利用されていないか確認必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private User isCheckId(HttpServletRequest request) {

		User user = new User();
		user.setLoginId(request.getParameter("login_id"));

		UserService UserService = new UserService();
		UserService.checkId(user);

		// IDが既に利用されていないか確認
		if (UserService.checkId(user) == null) {
			return null;
		} else {
			return user;
		}


	}


}
