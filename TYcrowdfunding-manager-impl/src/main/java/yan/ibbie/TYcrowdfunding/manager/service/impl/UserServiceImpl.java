package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Permission;
import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.exception.LoginFailException;
import yan.ibbie.TYcrowdfunding.manager.dao.UserMapper;
import yan.ibbie.TYcrowdfunding.manager.service.UserService;
import yan.ibbie.TYcrowdfunding.util.Const;
import yan.ibbie.TYcrowdfunding.util.MD5Util;
import yan.ibbie.TYcrowdfunding.util.Page;
import yan.ibbie.TYcrowdfunding.vo.Data;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;

	public User queryUserLogin(Map<String, Object> paraMap) {
		User user = userMapper.queryUserLogin(paraMap);
		if(user==null) {
			throw new LoginFailException("用户帐号或密码不正确！");
		}
		return user;
	}

	/*
	 * public Page queryPage(int pageno, int pageSize) { Page page = new
	 * Page(pageno, pageSize);
	 * 
	 * int count = userMapper.queryCount(); page.setTotalSize(count);
	 * 
	 * int startIndex = page.getStartIndex(); List<User> datas =
	 * userMapper.queryList(startIndex,pageSize); page.setDatas(datas);
	 * 
	 * return page; }
	 */

	public int saveUser(User user) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createtime = simpleDateFormat.format(new Date());
		user.setCreatetime(createtime);
		
		user.setUserpswd(MD5Util.digest(Const.PASSWORD));
		
		return userMapper.insert(user) ;
	}

	public Page queryPage(Map<String, Object> paramMap) {
		 Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pageSize"));
		 
		 //queryCount方法要根据所接收的参数不同进行不同的查询，所以此方法也需要修改成接收map参数
		 int count = userMapper.queryCount(paramMap);
		 page.setTotalSize(count);
		 
		 //queryList改成接收map参数了，不要忘记把startIndex放进map中进行传递
		 int startIndex = page.getStartIndex();
		 paramMap.put("startIndex", startIndex);
		 
		 List<User> datas = userMapper.queryList(paramMap);
		 page.setDatas(datas);
		 
		 return page; 
	}

	public User getUserById(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}

	public int updateUser(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	public int deleteUser(User user) {
		return userMapper.deleteByPrimaryKey(user.getId());
	}
	
	public int deleteBatchUser(List<User> userList) {
		int totalCount = 0;
		for (User user : userList) {
			int count = userMapper.deleteByPrimaryKey(user.getId());
			totalCount += count;
		}
		if (totalCount!=userList.size()) {
			throw new RuntimeException("批量删除失败");
		}
		return totalCount;
	}
	
	public int deleteBatchUserByVo(Data data) {
		return userMapper.deleteBatchUserByVo(data);
	}

	public int deleteBatchUser(Integer[] ids) {
		int totalCount = 0;
		for (Integer id : ids) {
			int count = userMapper.deleteByPrimaryKey(id);
			totalCount += count;
		}
		if (totalCount!=ids.length) {
			throw new RuntimeException("批量删除失败");
		}
		return totalCount;
	}

	public List<Role> queryAllRole() {
		return userMapper.queryAllRole();
	}

	public List<Integer> queryRoleIdByUserId(int id) {
		// TODO Auto-generated method stub
		return userMapper.queryRoleIdByUserId(id);
	}

	public int saveAssignRole(Integer userid, List<Integer> idList) {
		int count = 0;
		for (Integer roleid : idList) {
			int index = userMapper.insertRole(userid,roleid);
			count += index;
		}
		return count ;
	}

	public int deleteAssignRole(Integer userid, List<Integer> idList) {
		return userMapper.deleteRole(userid,idList);
	}

	public List<Permission> queryPermissionByUserid(Integer userid) {
		return userMapper.queryPermissionByUserid(userid);
	}
	
}
