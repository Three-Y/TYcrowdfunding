package yan.ibbie.TYcrowdfunding.manager.dao;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserLogin(Map<String, Object> paraMap);

	//int queryCount();

	//List<User> queryList(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);

	List<User> queryList(Map<String, Object> paramMap);

	int queryCount(Map<String, Object> paramMap);

	int deleteBatchUserByVo(Data data);

	List<Role> queryAllRole();

	List<Integer> queryRoleIdByUserId(int id);

	int insertRole(@Param("userid") Integer userid, @Param("roleid") Integer roleid);

	int deleteRole(@Param("userid") Integer userid, @Param("idList") List<Integer> idList);

	List<Permission> queryPermissionByUserid(Integer userid);


}