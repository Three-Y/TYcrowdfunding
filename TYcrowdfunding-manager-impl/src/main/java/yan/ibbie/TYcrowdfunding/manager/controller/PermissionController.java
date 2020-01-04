package yan.ibbie.TYcrowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.manager.service.PermissionService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("index")
	public String toIndex() {
		return "permission/index";
	}
	
	@RequestMapping("toAdd")
	public String toAdd() {
		return "permission/add";
	}
	
	@RequestMapping("toUpdate")
	public String toUpdate(Integer id,Map<String, Object> map) {
		
		Permission permission = permissionService.getPermissionById(id);
		
		map.put("permission" , permission);
		
		return "permission/update";
	}
	
	/*
	 * 删除许可
	 */
	@ResponseBody
	@RequestMapping("doDelete")
	public Object doDelete(Integer id) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = permissionService.deletePermissionById(id);
			
			if (count==1) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMessage("删除许可失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除许可失败！");
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 修改许可
	 */
	@ResponseBody
	@RequestMapping("doUpdate")
	public Object doUpdate(Permission permission) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = permissionService.updatePermission(permission);
			
			if (count==1) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMessage("修改许可失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改许可失败！");
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 添加许可
	 */
	@ResponseBody
	@RequestMapping("doAdd")
	public Object doAdd(Permission permission) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = permissionService.savePermission(permission);
			
			if (count==1) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMessage("添加许可失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("添加许可失败！");
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 显示许可树
	 */
	@ResponseBody
	@RequestMapping("doData")
	public Object doData() {
		AjaxResult result = new AjaxResult();
		
		List<Permission> zNodes = new ArrayList<Permission>();
		
		try {
			List<Permission> allPermissions = permissionService.getAllPermission();
			
			Map<Integer, Permission> map = new HashMap<Integer,Permission>();
			
			for (Permission permission : allPermissions) {
				map.put(permission.getId(), permission);
			}
			
			for (Permission child : allPermissions) {
				if (child.getPid()==null) {
					child.setOpen(true);
					zNodes.add(child);
				}else {
					Permission parent = map.get(child.getPid());
					parent.getChildren().add(child);
				}
			}
			
			result.setData(zNodes);
			result.setSuccess(true);
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("加载许可树失败！");
			e.printStackTrace();
		}

		return result;
	}
	
	//一次查询出所有permission，只访问一次数据库
	//循环次数过多，效率不高
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("doData") public Object doData() { AjaxResult result = new
	 * AjaxResult();
	 * 
	 * List<Permission> zNodes = new ArrayList<Permission>();
	 * 
	 * try { List<Permission> allPermissions = permissionService.getAllPermission();
	 * 
	 * for (Permission permission : allPermissions) { if (permission.getPid()==null)
	 * {//说明这是个根节点 permission.setOpen(true); zNodes.add(permission); }else
	 * {//说明这不是根节点 for (Permission parent : allPermissions) {
	 * //通过pid从所有permission中找到自己的父节点，然后将自己添加到父节点的子集合中 if
	 * (permission.getPid()==parent.getId()) { parent.getChildren().add(permission);
	 * break; } } } }
	 * 
	 * result.setData(zNodes); result.setSuccess(true);
	 * 
	 * } catch (Exception e) { result.setSuccess(false);
	 * result.setMessage("加载许可树失败！"); e.printStackTrace(); }
	 * 
	 * return result; }
	 */
	
	//多次访问数据库，效率低
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("doData") public Object doData() { AjaxResult result = new
	 * AjaxResult();
	 * 
	 * List<Permission> zNodes = new ArrayList<Permission>();
	 * 
	 * try { Permission root = permissionService.getRootPermission();
	 * 
	 * getChildrenPermissions(root);
	 * 
	 * // List<Permission> children =
	 * permissionService.queryPermissionByPid(node.getId()); // // for (Permission
	 * child : children) { //
	 * child.setChildren(permissionService.queryPermissionByPid(child.getId())); //
	 * } // // node.setChildren(children);
	 * 
	 * root.setOpen(true); zNodes.add(root);
	 * 
	 * result.setData(zNodes); result.setSuccess(true);
	 * 
	 * } catch (Exception e) { result.setSuccess(false);
	 * result.setMessage("加载许可树失败！"); e.printStackTrace(); }
	 * 
	 * return result; }
	 */
	
	//递归
	private void getChildrenPermissions(Permission permission){
		List<Permission> children = permissionService.getPermissionByPid(permission.getId());
		
		permission.setChildren(children);
		
		for (Permission child : children) {
			getChildrenPermissions(child);
		}
	}
}
