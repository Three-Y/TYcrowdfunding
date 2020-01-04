package yan.ibbie.TYcrowdfunding.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.manager.service.PermissionService;
import yan.ibbie.TYcrowdfunding.util.Const;

public class StartSystemListener implements ServletContextListener{
	/*
	 * 此方法在服务器启动时，创建ServletContext后调用
	 */
	public void contextInitialized(ServletContextEvent sce) {
		//向ServletContext中保存app根目录的路径，用于简化jsp中路径的编写
		ServletContext servletContext = sce.getServletContext();
		String contextPath = servletContext.getContextPath();
		servletContext.setAttribute("APP_PATH", contextPath);
		
		//获取所有许可路径，保存到application域中
		ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		PermissionService permissionService = (PermissionService)ioc.getBean(PermissionService.class);
		List<Permission> allPermission = permissionService.getAllPermission();
		Set<String> allUris = new HashSet<String>();
		for (Permission permission : allPermission) {
			allUris.add("/"+permission.getUrl());
		}
		servletContext.setAttribute(Const.ALL_PERMISSION_URI, allUris);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
