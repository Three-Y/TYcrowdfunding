package yan.ibbie.TYcrowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.manager.service.UserService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Page;
import yan.ibbie.TYcrowdfunding.util.StringUtil;
import yan.ibbie.TYcrowdfunding.vo.Data;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public String toIndex() {
		return "user/index";
	}
	
	@RequestMapping("/toAdd")
	public String add() {
		return "user/add";
	}
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id,Map<String, Object> map) {
		User user = userService.getUserById(id);
		map.put("user", user);
		return "user/update";
	}
	
	@RequestMapping("/assignRole")
	public String assignRole(int id,Map<String, Object> map) {
		
		List<Role> allRoleList = userService.queryAllRole();
		
		List<Integer> roleIds = userService.queryRoleIdByUserId(id);
		
		List<Role> leftRoleList = new ArrayList<Role>();
		List<Role> rightRoleList = new ArrayList<Role>();
		
		for (Role role : allRoleList) {
			if (roleIds.contains(role.getId())) {
				rightRoleList.add(role);
			}else {
				leftRoleList.add(role);
			}
		}
		
		map.put("rightRoleList", rightRoleList);
		map.put("leftRoleList", leftRoleList);
		
		return "user/assignRole";
	}
	
	//条件查询
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object doIndex(
			@RequestParam(value="pageno",required=false,defaultValue="1") int pageno,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			String queryText) {
	 
		AjaxResult result = new AjaxResult();
		try {
			//将参数都放到一个map中，这样只需用一个UserServiceImpl的方法便可满足有queryText和无queryText的情况
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageno", pageno);
			paramMap.put("pageSize", pageSize);
			if (StringUtil.isNotEmpty(queryText)) {
				if (queryText.contains("%")) {
					queryText = queryText.replaceAll("%", "\\\\%");
				}
				paramMap.put("queryText", queryText);
			}
			//需要修改queryPage方法接受的参数为map
			Page page = userService.queryPage(paramMap);
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("数据加载失败");
			e.printStackTrace();
			
		}
		return result ;
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(User user) {
		AjaxResult result = new AjaxResult();
		try {
			
			int index = userService.saveUser(user);
			result.setSuccess(index==1);
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("保存数据失败");
			e.printStackTrace();
			
		}
		return result ;
	}
	
	@ResponseBody
	@RequestMapping("/doUpdate")
	public Object doUpdate(User user) {
		AjaxResult result = new AjaxResult();
		try {
			
			int index = userService.updateUser(user);
			result.setSuccess(index==1);
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改数据失败");
			e.printStackTrace();
			
		}
		return result ;
	}
	
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(User user) {
		AjaxResult result = new AjaxResult();
		try {
			
			int index = userService.deleteUser(user);
			result.setSuccess(index==1);
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除数据失败");
			e.printStackTrace();
			
		}
		return result ;
	}
	
	//接收复杂对象的List集合
	@ResponseBody
	@RequestMapping("/doDeleteBatch")
	public Object doDeleteBatch(Data data) {
		AjaxResult result = new AjaxResult();
		try {
			
			int index = userService.deleteBatchUserByVo(data);
			result.setSuccess(index==data.getUserList().size());
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除数据失败");
			e.printStackTrace();
			
		}
		return result ;
	}
	
	/*
	 * 分配角色
	 */
	@ResponseBody
	@RequestMapping("/doAssignRole")
	public Object doAssignRole(Integer userid, Data data) {
		AjaxResult result = new AjaxResult();
		try {
			
			int index = userService.saveAssignRole(userid,data.getIdList());
			result.setSuccess(index==data.getIdList().size());
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("添加角色失败！");
			e.printStackTrace();
			
		}
		return result ;
	}
	
	/*
	 * 删除角色
	 */
	@ResponseBody
	@RequestMapping("/doUnAssignRole")
	public Object doUnAssignRole(Integer userid, Data data) {
		AjaxResult result = new AjaxResult();
		try {
			
			int index = userService.deleteAssignRole(userid,data.getIdList());
			result.setSuccess(index==data.getIdList().size());
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除角色失败！");
			e.printStackTrace();
			
		}
		return result ;
	}
	
//	@ResponseBody
//	@RequestMapping("/doDeleteBatch")
//	public Object doDeleteBatch(Integer[] id) {
//		AjaxResult result = new AjaxResult();
//		try {
//			
//			int index = userService.deleteBatchUser(id);
//			result.setSuccess(index==id.length);
//			
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setMessage("删除数据失败");
//			e.printStackTrace();
//			
//		}
//		return result ;
//	}
	
	//异步请求，增加@ResponseBody注解，数据也不需要放到map中了
//	@ResponseBody
//	@RequestMapping("/index")
//	public Object index(
//			@RequestParam(value="pageno",required=false,defaultValue="1") int pageno,
//			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize) {
//	 
//		 Page page;
//		 AjaxResult result = new AjaxResult();
//		try {
//			page = userService.queryPage(pageno,pageSize);
//			result.setSuccess(true);
//			result.setPage(page);
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setMessage("数据加载失败");
//			e.printStackTrace();
//			
//		}
//		return result ;
//	}
	
	//同步请求
	/*
	 * @RequestMapping("/index") public Page index(
	 * 
	 * @RequestParam(value="pageno",required=false,defaultValue="1") int pageno,
	 * 
	 * @RequestParam(value="pageSize",required=false,defaultValue="10") int
	 * pageSize, Map<String,Object> map) {
	 * 
	 * Page page = userService.queryPage(pageno,pageSize); map.put("page", page);
	 * 
	 * return page; }
	 */
	 
}
