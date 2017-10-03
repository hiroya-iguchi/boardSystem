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

import boardSystem.beans.User;
import boardSystem.service.UserService;

@WebServlet(urlPatterns = { "/settings" })
@MultipartConfig(maxFileSize = 100000)
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User editUser = new UserService().getUser(Integer.parseInt(request.getParameter("id")));
		session.setAttribute("editUser", editUser);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		if (isValid(request, messages) == true) {
				new UserService().update(editUser);
				session.removeAttribute("editUser");
				response.sendRedirect("./");


		} else {
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		User editUser = (User) session.getAttribute("editUser");
		editUser.setName(request.getParameter("name"));
		editUser.setLoginId(request.getParameter("login_id"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
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

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}else if(!(name.length() <= 10) ){
			messages.add("名前は10文字以下で入力してください");

		}

		if(password.matches("^[ 　¥t¥n¥x0B¥f¥r]+$") ){
			messages.add("パスワードを入力してください");

		}

		if(!password.matches("^[0-9a-zA-Z]{6,20}$") && StringUtils.isEmpty(password) != true){
			messages.add("パスワードは半角英数字で6文字以上20文字以下で入力してください");

		}else if(!(password.equals(checkPassword))){
			messages.add("パスワードが一致しません");

		}

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}else if(!loginId.matches("^[\\p{Punct}0-9a-zA-Z]{6,20}$") ){
			messages.add("ログインIDは半角英数字および記号で6文字以上20文字以下で入力してください");

		}

		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
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
