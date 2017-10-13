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
import boardSystem.service.UserService;

@WebFilter("/*")
public class LoginFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String path = ((HttpServletRequest)request).getServletPath();

		 if(!path.equals("/login") && !path.matches(".*css.*")){
			 	User user = (User)((HttpServletRequest)request).getSession().getAttribute("loginUser") ;

			if (user == null || user.getIsWorking() == 0) {

				((HttpServletResponse)response).sendRedirect("login");

			}else{
				((HttpServletRequest) request).getSession().setAttribute("loginUser", new UserService().getUser(user.getId()));
				user = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");

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