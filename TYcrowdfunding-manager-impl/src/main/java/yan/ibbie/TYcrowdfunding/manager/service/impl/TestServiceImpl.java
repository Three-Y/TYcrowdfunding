package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.manager.dao.TestDao;
import yan.ibbie.TYcrowdfunding.manager.service.TestService;

@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private TestDao testDao;
	
	public void insert() {
		Map map = new HashMap();
		map.put("test", "测试");
		testDao.insert(map);
	}
	
}
