package yan.ibbie.TYcrowdfunding.manager.dao;

import yan.ibbie.TYcrowdfunding.bean.ProjectType;
import java.util.List;

public interface ProjectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectType record);

    ProjectType selectByPrimaryKey(Integer id);

    List<ProjectType> selectAll();

    int updateByPrimaryKey(ProjectType record);
}