package yan.ibbie.TYcrowdfunding.manager.service;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Ticket;

public interface AuthcertService {

	Ticket queryTicketByPiid(String processDefinitionId);

	Member queryMemberByTicket(Ticket ticket);
	
}
