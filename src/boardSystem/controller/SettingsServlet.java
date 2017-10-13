package boardSystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import boardSystem.beans.Branch;
import boardSystem.beans.Department;
import boardSystem.beans.User;
import boardSystem.service.BranchService;
import boardSystem.service.DepartmentService;
import boardSystem.service.UserService;

@WebServlet(urlPatterns = { "/settings" })
@MultipartConfig(maxFileSize = 100000)
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		 if(request.getParameter("id")==null){
			messages.add("該当のユーザーが存在しません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("management");
		}
		 else if(!(request.getParameter("id")).matches("^\\d{1,4}$")){
			messages.add("該当のユーザーが存在しません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("management");
		}else if(new UserService().getUser(Integer.parseInt(request.getParameter("id"))) == null){
			messages.add("該当のユーザーが存在しません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("management");
		}
		else{
			List<Branch> branches = new BranchService().getBranches();
			List<Department> departments = new DepartmentService().getDepartments();

			User editUser = new UserService().getUser(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("editUser", editUser);
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);

			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		User editUser = getEditUser(request);
		request.setAttribute("editUser", editUser);

		if (isValid(request, messages) == true) {
			new UserService().update(editUser);
			request.removeAttribute("editUser");
			response.sendRedirect("management");

		} else {
			List<Branch> branches = new BranchService().getBranches();
			List<Department> departments = new DepartmentService().getDepartments();
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);
			request.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		User editUser = new User();
			editUser.setId(Integer.parseInt(request.getParameter("id")));
			editUser.setName(request.getParameter("name"));
			editUser.setLoginId(request.getParameter("login_id"));
			editUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
			editUser.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
			editUser.setBranchName(request.getParameter("branchName"));
			editUser.setDepartmentName(request.getParameter("departmentName"));
			editUser.setPassword(request.getParameter("password"));
			return editUser;
	}



	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("password2");
		String loginId = request.getParameter("login_id");
		int branchId = Integer.parseInt(request.getParameter("branch_id"));
		int departmentId = Integer.parseInt(request.getParameter("department_id"));

		//ログインIDで重複かつ、idが自分のでない場合
		if (isLoginId(request) == false && isId(request) == false) {
			messages.add("すでに使用されているログインIDです");
		}

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}else if(!loginId.matches("^[0-9a-zA-Z]{6,20}$") ){
			messages.add("ログインIDは半角英数字で6文字以上20文字以下で入力してください");

		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}else if(!(name.length() <= 10) ){
			messages.add("名前は10文字以下で入力してください");

		}

		if(password.matches("^[ 　¥t¥n¥x0B¥f¥r]+$") ){
			messages.add("パスワードを入力してください");

		}

		if(!password.matches("^[\\p{Punct}0-9a-zA-Z]{6,20}$") && StringUtils.isEmpty(password) != true){
			messages.add("パスワードは半角英数字および記号で6文字以上20文字以下で入力してください");

		}else if(!password.equals(checkPassword)){
			messages.add("パスワードが一致しません");

		}

		if(branchId == 1 ){
			if(departmentId == 3 || departmentId == 4){
				messages.add("支店名と部署・役職の組み合わせが不適切です");
			}
		}

		if(branchId == 2  ){
			if(departmentId == 1 || departmentId == 2){
				messages.add("支店名と部署・役職の組み合わせが不適切です");
			}
		}


		if(branchId == 3 ){
			if(departmentId == 1 || departmentId == 2){
				messages.add("支店名と部署・役職の組み合わせが不適切です");
			}
		}


		if(branchId == 4 ){
			if(departmentId == 1 || departmentId == 2){
				messages.add("支店名と部署・役職の組み合わせが不適切です");
			}
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isLoginId(HttpServletRequest request) {


		User user = new UserService().getUser(request.getParameter("login_id"));

		// IDが既に利用されていないか確認
		if (user != null) {
			return false;
		} else {
			return true;
		}


	}

	private boolean isId(HttpServletRequest request) {

		User user = new UserService().getUser(request.getParameter("login_id"));


		// IDが既に利用されていないか確認
		if (user.getId() != Integer.parseInt(request.getParameter("id"))) {
			return false;
		} else {
			return true;
		}


	}

}
