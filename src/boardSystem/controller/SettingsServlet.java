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

import boardSystem.beans.BoardUser;
import boardSystem.service.BoardUserService;

@WebServlet(urlPatterns = { "/boardSettings" })
@MultipartConfig(maxFileSize = 100000)
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		BoardUser editUser = new BoardUserService().getUser(Integer.parseInt(request.getParameter("id")));
		session.setAttribute("editUser", editUser);

		request.getRequestDispatcher("boardSettings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		BoardUser editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		if (isValid(request, messages) == true) {
				new BoardUserService().update(editUser);
				session.removeAttribute("editUser");
				response.sendRedirect("./");


		} else {
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("boardSettings.jsp").forward(request, response);
		}
	}

	private BoardUser getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		BoardUser editUser = (BoardUser) session.getAttribute("editUser");
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

		if (isCheckId(request) == true) {
			messages.add("すでに使用されているログインIDです");
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

	private boolean isCheckId(HttpServletRequest request) {

		BoardUser user = new BoardUser();
		user.setLoginId(request.getParameter("login_id"));

		BoardUserService UserService = new BoardUserService();
		UserService.checkId(user);

		// IDが既に利用されていないか確認
		if (UserService.checkId(user) == true) {
			return true;
		} else {
			return false;
		}


	}

}
