package yan.ibbie.TYcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.util.Page;
import yan.ibbie.TYcrowdfunding.vo.Data;

public interface UserService {

	User queryUserLogin(Map<String, Object> paraMap);

	//Page queryPage(int pageno, int pageSize);

	int saveUser(User user);

	Page queryPage(Map<String, Object> paramMap);

	User getUserById(Integer id);

	int updateUser(User user);

	int deleteUser(User user);

	int deleteBatchUser(Integer[] ids);
	
	int deleteBatchUserByVo(Data data);

	List<Role> queryAllRole();

	List<Integer> queryRoleIdByUserId(int id);

	int saveAssignRole(Integer userid, List<Integer> idList);

	int deleteAssignRole(Integer userid, List<Integer> idList);

	List<Permission> queryPermissionByUserid(Integer userid);
}
