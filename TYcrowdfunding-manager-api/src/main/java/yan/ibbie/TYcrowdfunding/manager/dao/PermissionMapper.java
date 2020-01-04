package yan.ibbie.TYcrowdfunding.manager.dao;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

	Permission queryRootPermission();

	List<Permission> queryPermissionByPid(Integer id);

	List<Integer> selectPermissionIdByRoleid(Integer roleid);
}