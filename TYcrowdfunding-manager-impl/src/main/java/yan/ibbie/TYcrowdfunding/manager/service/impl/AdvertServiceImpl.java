package yan.ibbie.TYcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Advertisement;
import yan.ibbie.TYcrowdfunding.manager.dao.AdvertisementMapper;
import yan.ibbie.TYcrowdfunding.manager.service.AdvertService;
import yan.ibbie.TYcrowdfunding.util.Page;

@Service
public class AdvertServiceImpl implements AdvertService{

	@Autowired
	AdvertisementMapper advertisementMapper;
	
	public Integer saveAdvert(Advertisement advertisement) {
		return advertisementMapper.insert(advertisement);
	}

	public Page queryPage(Map<Object, Object> paramMap) {
		
		Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pageSize"));

		int startIndex = page.getStartIndex();
		paramMap.put("startIndex", startIndex);
		
		int count = advertisementMapper.queryCount(paramMap);
		page.setTotalSize(count);
		
		List<Advertisement> list = advertisementMapper.queryList(paramMap);
		page.setDatas(list);
		
		return page;
	}

}
