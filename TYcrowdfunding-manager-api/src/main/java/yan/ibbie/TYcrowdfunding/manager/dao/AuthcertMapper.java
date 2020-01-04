package yan.ibbie.TYcrowdfunding.manager.dao;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Ticket;

public interface AuthcertMapper {

	Ticket queryTicketByPiid(String processDefinitionId);

	Member queryMemberByTicket(Ticket ticket);
	
}
