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

import boardSystem.beans.Comments;
import boardSystem.beans.UserMessage;
import boardSystem.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		String startDate = "2017-09-01 00:00:00";
		String endDate ="2018-09-01 00:00:00";
//		String category = null ;
		String[] category = null;
		String strCategory = request.getParameter("category");


		if(!StringUtils.isEmpty(request.getParameter("startDate"))){
			startDate = request.getParameter("startDate")+ " 00:00:00";
			request.setAttribute("startDate", request.getParameter("startDate"));

		}
		if(!StringUtils.isEmpty(request.getParameter("endDate"))) {
			endDate = request.getParameter("endDate")+" 23:59:59";
			request.setAttribute("endDate", request.getParameter("endDate"));
		}
		if(!StringUtils.isEmpty(request.getParameter("category")) ) {

			category = request.getParameter("category").split("[　 ]");

			if(category.length >= 2){
			List<String> list = new ArrayList<String>();
			list.add(category[0]);
			list.add(category[1]);

			}
		}



		List<UserMessage> messages = new MessageService().getMessage(startDate, endDate, category);
		List<Comments> comments = new MessageService().getAllComment();
//		カテゴリー一覧の取得
		ArrayList<String> categorys = new ArrayList<String>();
			for(UserMessage message : messages){
				boolean boo = true;
				for(String str : categorys){
					if(message.getCategory().equals(str)){
						boo = false;
					}
				}
				if(boo){
					categorys.add(message.getCategory());
				}
			}

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.setAttribute("category", category);
		request.setAttribute("categorys", categorys);
		request.setAttribute("strCategory", strCategory);

		request.getRequestDispatcher("boardTop.jsp").forward(request, response);
	}


}
