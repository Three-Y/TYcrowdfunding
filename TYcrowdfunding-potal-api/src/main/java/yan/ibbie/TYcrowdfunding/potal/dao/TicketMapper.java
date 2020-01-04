package yan.ibbie.TYcrowdfunding.potal.dao;

import java.util.List;
import java.util.Map;

import yan.ibbie.TYcrowdfunding.bean.Ticket;

public interface TicketMapper {
	int deleteByPrimaryKey(Integer id);

    int insert(Ticket ticket);

    Ticket selectByPrimaryKey(Integer id);

    List<Ticket> selectAll();

    int updateByPrimaryKey(Ticket ticket);

	Ticket selByMemberId(Integer memberId);

}
