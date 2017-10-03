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

import boardSystem.beans.Message;
import boardSystem.beans.User;
import boardSystem.service.MessageService;

@WebServlet(urlPatterns = { "/message" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String text = request.getParameter("text");
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");

			Message message = new Message();
			message.setTitle(request.getParameter("title"));
			message.setText(request.getParameter("text"));
			message.setCategory(request.getParameter("category"));
			message.setBranchId(user.getBranchId());
			message.setDepartmentId(user.getDepartmentId());
			message.setUserId(user.getId());

			new MessageService().register(message);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("title", title);
			request.setAttribute("text", text);
			request.setAttribute("category", category);
		    request.getRequestDispatcher("message.jsp").forward(request, response);
			response.sendRedirect("message");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String text = request.getParameter("text");
		String title = request.getParameter("title");
		String category = request.getParameter("category");

		if (text.matches("^[ 　¥t¥n¥x0B¥f¥r]+$") || StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください");
		}else if(!(text.length() <= 1000)){
			messages.add("本文は1000文字以内で入力してください");
		}

		if (title.matches("^[ 　¥t¥n¥x0B¥f¥r]+$") || StringUtils.isEmpty(title) == true) {
			messages.add("件名を入力してください");
		}else if(!(title.length() <= 30)){
			messages.add("件名は30文字以内で入力してください");
		}

		if (category.matches("^[ 　¥t¥n¥x0B¥f¥r]+$") || StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを入力してください");
		}else if(!(category.length() <= 10)){
			messages.add("カテゴリーは10文字以内で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
