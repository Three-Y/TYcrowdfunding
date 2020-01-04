package yan.ibbie.TYcrowdfunding.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.manager.service.PermissionService;
import yan.ibbie.TYcrowdfunding.util.Const;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//取到要控制访问的全部uri
		ServletContext servletContext = request.getSession().getServletContext();
		Set<String> allUris = (Set<String>)servletContext.getAttribute(Const.ALL_PERMISSION_URI);
		
		String servletPath = request.getServletPath();
		
		if (allUris.contains(servletPath)) {//如果请求的路径是有权限控制的路径
			
			//取出用户可访问的uri
			HttpSession session = request.getSession();
			Set<String> userUris = (Set<String>)session.getAttribute(Const.USER_URIS);
			
			if (userUris.contains(servletPath)) {
				return true;
			}else {
				response.sendRedirect(request.getContextPath()+"/login.htm");
				return false;
			}
			
		}else {//如果请求的路径没有权限控制
			return true;
		}
		
	}
	
}
