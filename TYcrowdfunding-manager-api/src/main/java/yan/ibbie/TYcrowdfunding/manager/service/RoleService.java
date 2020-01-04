package yan.ibbie.TYcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.util.Page;

public interface RoleService {

	List<Role> getAllRole();

	Page queryPage(Map<String, Object> pageParam);

	int saveRolePermissionRelationship(Integer roleid, List<Integer> idList);

}
