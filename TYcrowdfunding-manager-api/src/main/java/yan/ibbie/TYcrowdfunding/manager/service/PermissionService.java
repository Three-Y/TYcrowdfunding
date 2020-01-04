package yan.ibbie.TYcrowdfunding.manager.service;

import java.util.List;

import yan.ibbie.TYcrowdfunding.bean.Permission;

public interface PermissionService {

	Permission getRootPermission();

	List<Permission> getPermissionByPid(Integer id);

	List<Permission> getAllPermission();

	int savePermission(Permission permission);

	Permission getPermissionById(Integer id);

	int updatePermission(Permission permission);

	int deletePermissionById(Integer id);

	List<Integer> getPermissionIdByRoleid(Integer roleid);

}
