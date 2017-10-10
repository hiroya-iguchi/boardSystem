package boardSystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardSystem.beans.User;

@WebFilter("/*")
public class LoginFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String path = ((HttpServletRequest)request).getServletPath();


		 if(!path.equals("/login") || !path.matches(".*login.*") ){
			 	User user = (User)((HttpServletRequest)request).getSession().getAttribute("loginUser") ;
//				user = new UserService().getUser(user.getId(););


			if (user == null || user.getIsWorking() == 0) {

//				if(!path.equals("./")){
//				messages.add("ログインして下さい");
//				session.setAttribute("errorMessages", messages);
////				request.getRequestDispatcher("login.jsp").forward(request, response);
//				}
				((HttpServletResponse)response).sendRedirect("login");

			}else{

				if(path.equals("/management") || path.matches(".*settings.*") || path.equals("/signup") ){
					if(user.getDepartmentId() !=1 ){
					((HttpServletResponse)response).sendRedirect("./");
					}
					else{
					chain.doFilter(request, response); // サーブレットを実行
					}
				}else{
					chain.doFilter(request, response); // サーブレットを実行
				}
			}
		}else{
			chain.doFilter(request, response); // サーブレットを実行
		}
	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}

}