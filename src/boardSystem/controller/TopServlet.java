package boardSystem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import boardSystem.beans.BoardUserMessage;
import boardSystem.beans.Comments;
import boardSystem.service.BoardMessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		String startDate = "2017-09-01 00:00:00";
		String endDate ="2018-09-01 00:00:00";
		String category = "" ;


		if(StringUtils.isEmpty(request.getParameter("startDate")) == false){
			startDate = request.getParameter("startDate")+ " 00:00:00";

		}
		if(StringUtils.isEmpty(request.getParameter("endDate")) == false) {
			endDate = request.getParameter("endDate")+" 23:59:59";
		}
		if(StringUtils.isEmpty(request.getParameter("category")) == false) {
			category = request.getParameter("category") ;
		}


		if(request.getParameter("category") == null || request.getParameter("category") ==""){
			List<BoardUserMessage> refineMessages = new BoardMessageService().getMessage(startDate , endDate);
			request.setAttribute("messages", refineMessages);

		}else{
//			BoardUserMessage refine = new BoardUserMessage();
//			refine.setCategory(request.getParameter("category"));

			List<BoardUserMessage> refineCategory = new BoardMessageService().refine(category);
			System.out.println(request.getParameter("category") );
			request.setAttribute("messages", refineCategory);
		}



		List<Comments> comments = new BoardMessageService().getComment();
			request.setAttribute("comments", comments);

		request.getRequestDispatcher("boardTop.jsp").forward(request, response);
	}


}
