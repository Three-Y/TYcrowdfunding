package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.manager.dao.PermissionMapper;
import yan.ibbie.TYcrowdfunding.manager.service.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionMapper permissionMapper;

	public Permission getRootPermission() {
		return permissionMapper.queryRootPermission();
	}

	public List<Permission> getPermissionByPid(Integer id) {
		return permissionMapper.queryPermissionByPid(id);
	}

	public List<Permission> getAllPermission() {
		return permissionMapper.selectAll();
	}

	public int savePermission(Permission permission) {
		return permissionMapper.insert(permission);
	}

	public Permission getPermissionById(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	public int updatePermission(Permission permission) {
		return permissionMapper.updateByPrimaryKey(permission);
	}

	public int deletePermissionById(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	public List<Integer> getPermissionIdByRoleid(Integer roleid) {
		return permissionMapper.selectPermissionIdByRoleid(roleid);
	}
}
