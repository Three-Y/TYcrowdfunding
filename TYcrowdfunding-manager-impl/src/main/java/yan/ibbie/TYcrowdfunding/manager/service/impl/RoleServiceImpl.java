package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.RolePermission;
import yan.ibbie.TYcrowdfunding.manager.dao.RoleMapper;
import yan.ibbie.TYcrowdfunding.manager.service.RoleService;
import yan.ibbie.TYcrowdfunding.util.Page;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleMapper roleMapper;

	public List<Role> getAllRole() {
		return roleMapper.selectAll();
	}

	public Page queryPage(Map<String, Object> pageParam) {
		
		Page page = new Page((Integer)pageParam.get("pageno"), (Integer)pageParam.get("pageSize"));
		
		int count = roleMapper.queryCount();
		page.setTotalSize(count);
		
		int startIndex = page.getStartIndex();
		pageParam.put("startIndex", startIndex);
		
		List<Role> roles = roleMapper.queryList(pageParam);
		page.setDatas(roles);
		
		return page;
	}

	public int saveRolePermissionRelationship(Integer roleid, List<Integer> idList) {
		
		roleMapper.deleteRolePermissionRelationship(roleid);
		
		List<RolePermission> rolePermissionList = new ArrayList<RolePermission>();
		
		for (Integer permissionid : idList) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionid(permissionid);
			rolePermission.setRoleid(roleid);
			rolePermissionList.add(rolePermission);
		}
		
		int count = roleMapper.insertRolePermissionRelationshipBatch(rolePermissionList);
		
		return count;
	}
}
