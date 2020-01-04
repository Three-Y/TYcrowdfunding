package yan.ibbie.TYcrowdfunding.manager.service;

import java.util.Map;

import yan.ibbie.TYcrowdfunding.bean.Advertisement;
import yan.ibbie.TYcrowdfunding.util.Page;

public interface AdvertService {

	Integer saveAdvert(Advertisement advertisement);

	Page queryPage(Map<Object, Object> paramMap);

}
