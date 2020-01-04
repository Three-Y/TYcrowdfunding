package yan.ibbie.TYcrowdfunding.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.util.Const;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//定义不需要拦截的路径（白名单）
		Set<String> uri = new HashSet<String>();
		uri.add("/user/reg.do");
		uri.add("/user/reg.htm");
		uri.add("/login.htm");
		uri.add("/doLogin.do");
		uri.add("/logout.do");
		uri.add("/index.htm");
		
		//获取请求路径，也就是"http://localhost:8080/TYcrowdfunding-main/"后面的部分
		String servletPath = request.getServletPath();
		
		//如果请求路径在白名单范围中则放行
		if (uri.contains(servletPath)) {
			return true;
		}
		
		//判断用户是否登陆
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Const.LOGIN_USER);
		Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		
		if (user!=null || member!=null) {
			return true;
		}else {
			response.sendRedirect(request.getContextPath()+"/login.htm");
			return false;
		}
		
	}
	
}
