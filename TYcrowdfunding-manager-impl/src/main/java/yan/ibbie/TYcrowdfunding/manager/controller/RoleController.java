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

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.manager.service.PermissionService;
import yan.ibbie.TYcrowdfunding.manager.service.RoleService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Page;
import yan.ibbie.TYcrowdfunding.vo.Data;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;
	
	@RequestMapping("/toAssignPermission")
	public String toAssignPermission() {
		return "role/assignPermission";
	}
	
	@RequestMapping("/index")
	public String toIndex() {
		return "role/index";
	}
	
	@ResponseBody
	@RequestMapping("/doAssignPermission")
	public Object doAssignPermission(Integer roleid ,Data data) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			int count = roleService.saveRolePermissionRelationship(roleid,data.getIdList());
			
			if (count==data.getIdList().size()) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMessage("保存数据失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("保存数据失败！");
			e.printStackTrace();
			
		}
		//异步加载树，返回的必须是一个集合，所以直接返回zNodes，而不是返回AjaxResult
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doDataAsync")
	public Object doDataAsync(Integer roleid) {
		
		List<Permission> zNodes = new ArrayList<Permission>();
		
		try {
			
			List<Integer> allocatedPermissionId = permissionService.getPermissionIdByRoleid(roleid);
			
			List<Permission> allPermissions = permissionService.getAllPermission();
			
			Map<Integer, Permission> map = new HashMap<Integer,Permission>();
			for (Permission permission : allPermissions) {
				if (allocatedPermissionId.contains(permission.getId())) {
					permission.setChecked(true);
				}
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//异步加载树，返回的必须是一个集合，所以直接返回zNodes，而不是返回AjaxResult
		return zNodes;
	}
	
	@ResponseBody
	@RequestMapping("/doData")
	public Object doData(
			@RequestParam(value="pageno",required=false,defaultValue="1") int pageno,
			@RequestParam(value="pageSize",required=false,defaultValue="3") int pageSize) {
		AjaxResult result = new AjaxResult();
		Map<String, Object> pageParam = new HashMap<String, Object>();
		pageParam.put("pageno", pageno);
		pageParam.put("pageSize", pageSize);
		try {
			Page page = roleService.queryPage(pageParam);
			
			result.setPage(page);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("加载数据失败！");
			e.printStackTrace();
		}
		
		return result;
	}
}
