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

import boardSystem.beans.BoardUser;
import boardSystem.service.BoardUserService;

//URLパターンを指定するアノテーション
@WebServlet(urlPatterns = { "/boardSignup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("boardSignUp.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		if (isValid(request, messages) == true) {

			BoardUser user = new BoardUser();
			user.setLoginId(request.getParameter("login_id"));
			user.setName(request.getParameter("name"));
			user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
			user.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));
			user.setPassword(request.getParameter("password"));

			new BoardUserService().register(user);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("boardSignup");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String checkPassword =request.getParameter("password2");

		if (isCheckId(request) == true) {
			messages.add("すでに使用されているログインIDです");
		}

		if (StringUtils.isEmpty(id) == true) {
			messages.add("ログインIDを入力してください");
		}else if(!id.matches("^[\\p{Punct}0-9a-zA-Z]{6,20}$") ){
			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");

		}


		if (StringUtils.isEmpty(password) == true || StringUtils.isEmpty(checkPassword) == true) {
			messages.add("パスワードを入力してください");
		}else if(!password.matches("^[0-9a-zA-Z]{6,20}$") ){
				messages.add("パスワードは半角英数字（記号含む）6文字以上20文字以下で入力してください");
		}else if(!(password.equals(checkPassword))){
			messages.add("パスワードが一致しません");

		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}else if(!(name.length() <= 10) ){
				messages.add("名前は10文字以下で入力してください");

		}


		// TODO アカウントが既に利用されていないか確認必要
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
