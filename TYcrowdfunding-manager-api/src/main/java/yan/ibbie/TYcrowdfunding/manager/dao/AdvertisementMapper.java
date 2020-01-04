package yan.ibbie.TYcrowdfunding.manager.dao;

import yan.ibbie.TYcrowdfunding.bean.Advertisement;
import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

	int queryCount(Map<Object, Object> paramMap);

	List<Advertisement> queryList(Map<Object, Object> paramMap);
}