package yan.ibbie.TYcrowdfunding.manager.dao;

import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.RolePermission;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

	int queryCount();

	List<Role> queryList(Map<String, Object> pageParam);

	void deleteRolePermissionRelationship(Integer roleid);

	int insertRolePermissionRelationshipBatch(List<RolePermission> rolePermissionList);
}