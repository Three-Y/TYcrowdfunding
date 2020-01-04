package yan.ibbie.TYcrowdfunding.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.manager.service.UserService;
import yan.ibbie.TYcrowdfunding.potal.service.MemberService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Const;
import yan.ibbie.TYcrowdfunding.util.MD5Util;

@Controller
public class DispatcherController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MemberService memberService;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpSession session) {
		
		boolean needLogin = true;
		String type = "";
		Cookie[] cookies = request.getCookies();
		
		//判断是否有登陆的cookie
		if (cookies!=null) {
			for (Cookie cookie : cookies) {//循环每个cookie
				if (cookie.getName().equals("loginCode")) {//找到loginCode的cookie
					String loginCode = cookie.getValue();
					System.out.println("从cookie中拿到的键值对是：键="+cookie.getName()+"，值="+loginCode);
					//处理字符串的数据
					String[] split = loginCode.split("&");
					if (split.length==3) {
						String loginacct = split[0].split("=")[1] ;
						String userpswd = split[1].split("=")[1] ;
						type = split[2].split("=")[1] ;
						Map<String, Object> paraMap = new HashMap<String, Object>();
						paraMap.put("loginacct", loginacct);
						paraMap.put("userpswd", userpswd);
						paraMap.put("type", type);
						
						try {
							if (type.equals("member")) {//如果是会员
								
								Member member = memberService.queryMemberLogin(paraMap);
								session.setAttribute(Const.LOGIN_MEMBER, member);
								needLogin = false;
								
							}else if (type.equals("user")) {//如果是管理
								
								User user = userService.queryUserLogin(paraMap);
								
								//-------------以下是用于拦截器验证的操作----------------
								
								List<Permission> permissionList = userService.queryPermissionByUserid(user.getId());
								
								Set<String> userUris = new HashSet<String>();//保存用户可访问的uri，用于拦截器的验证
								
								Permission permissionRoot = new Permission();
								
								Map<Integer, Permission> map = new HashMap<Integer,Permission>();
								
								for (Permission permission : permissionList) {
									map.put(permission.getId(), permission);
									userUris.add("/"+permission.getUrl());
								}
								
								for (Permission child : permissionList) {
									if (child.getPid()==null) {
										child.setOpen(true);
										permissionRoot = child;
									}else {
										Permission parent = map.get(child.getPid());
										parent.getChildren().add(child);
									}
								}
								
								session.setAttribute("permissionRoot", permissionRoot);
								session.setAttribute(Const.USER_URIS, userUris);
								
								//--------------以上是用于拦截器验证的操作---------------
								
								session.setAttribute(Const.LOGIN_USER, user);
								needLogin = false;
								
							}
						} catch (Exception e) {
							e.printStackTrace();
							needLogin = true;
						}
					}
				}
			}
		}
		
		if (needLogin) {
			return "login";
		}else if (type.equals("member")) {
			return "member/index";
		}else if (type.equals("user")) {
			return "main";
		}
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//销毁session对象，或者清楚session的内容
		session.invalidate();
		return "redirect:/index.htm";
	}
	
	@RequestMapping("/main")
	public String main(HttpSession session) {
		return "main";
	}
	
	//异步请求
	@ResponseBody//此注解表示会将返回值转换成json串，而不是解析成视图名
	@RequestMapping("/doLogin")
	public Object doLogin(String loginacct,String userpswd,String type,String rememberMe,HttpSession session,HttpServletResponse response) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("loginacct", loginacct);
			paraMap.put("userpswd", MD5Util.digest(userpswd));
			paraMap.put("type", type);
			
			//判断用户类型是会员还是管理
			if ("member".equals(type)) {
				Member member = memberService.queryMemberLogin(paraMap);
				
				session.setAttribute(Const.LOGIN_MEMBER, member);
				
				result.setSuccess(true);
				
			}else if ("user".equals(type)) {
				User user = userService.queryUserLogin(paraMap);
				
				//-----------------------------
				
				List<Permission> permissionList = userService.queryPermissionByUserid(user.getId());
				
				Set<String> userUris = new HashSet<String>();//保存用户可访问的uri，用于拦截器的验证
				
				Permission permissionRoot = new Permission();
				
				Map<Integer, Permission> map = new HashMap<Integer,Permission>();
				
				for (Permission permission : permissionList) {
					map.put(permission.getId(), permission);
					userUris.add("/"+permission.getUrl());
				}
				
				for (Permission child : permissionList) {
					if (child.getPid()==null) {
						child.setOpen(true);
						permissionRoot = child;
					}else {
						Permission parent = map.get(child.getPid());
						parent.getChildren().add(child);
					}
				}
				
				session.setAttribute("permissionRoot", permissionRoot);
				session.setAttribute(Const.USER_URIS, userUris);
				
				//-----------------------------
				
				session.setAttribute(Const.LOGIN_USER, user);
				result.setSuccess(true);
			}
			
			//如果勾选了”记住我“
			if (rememberMe.equals("1")) {
				String loginCode = "\"loginacct="+loginacct+"&userpswd="+paraMap.get("userpswd")+"&type="+type+"\"";
				System.out.println("cookie的内容："+loginCode);
				Cookie cookie = new Cookie("loginCode", loginCode);
				cookie.setMaxAge(60*60*24*14);//设置cookie过期时间，单位秒
				cookie.setPath("/");//表示所有路径都可以访问此cookie
				response.addCookie(cookie);
			}
			
		} catch (Exception e) {
			result.setMessage("登陆失败");
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	//同步请求
	/*
	 * @RequestMapping("/doLogin") public String doLogin(String loginacct,String
	 * userpswd,String type,HttpSession session) { Map<String, Object> paraMap = new
	 * HashMap<String, Object>(); paraMap.put("loginacct", loginacct);
	 * paraMap.put("userpswd", userpswd); paraMap.put("type", type);
	 * System.out.println("这里是aaaaaaaaaaaaaa"); User user =
	 * userService.queryUserLogin(paraMap); System.out.println("这里是bbbbbbbbbbbb");
	 * session.setAttribute(Const.LOGIN_USER, user);
	 * System.out.println("这里是cccccccccccccccc"); //因为直接return
	 * "main";是转发操作，会有重复提交表单的可能 //所以使用重定向前缀，使用了前缀后视图解析器不会自动添加前后缀 return
	 * "redirect:/main.htm"; }
	 */
}
