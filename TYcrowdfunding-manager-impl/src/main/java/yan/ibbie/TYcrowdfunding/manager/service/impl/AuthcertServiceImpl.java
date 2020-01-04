package yan.ibbie.TYcrowdfunding.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Ticket;
import yan.ibbie.TYcrowdfunding.manager.dao.AuthcertMapper;
import yan.ibbie.TYcrowdfunding.manager.service.AuthcertService;
@Service
public class AuthcertServiceImpl implements AuthcertService {
	@Autowired
	AuthcertMapper authcertMapper;

	public Ticket queryTicketByPiid(String processDefinitionId) {
		return authcertMapper.queryTicketByPiid(processDefinitionId);
	}

	public Member queryMemberByTicket(Ticket ticket) {
		return authcertMapper.queryMemberByTicket(ticket);
	}
}
