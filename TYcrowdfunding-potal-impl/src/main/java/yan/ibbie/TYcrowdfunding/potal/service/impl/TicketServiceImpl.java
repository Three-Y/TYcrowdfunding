package yan.ibbie.TYcrowdfunding.potal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yan.ibbie.TYcrowdfunding.bean.Ticket;
import yan.ibbie.TYcrowdfunding.potal.dao.TicketMapper;
import yan.ibbie.TYcrowdfunding.potal.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	TicketMapper ticketMapper;
	
	public Ticket selByMemberId(Integer memberId) {
		return ticketMapper.selByMemberId(memberId);
	}

	public void saveTicket(Ticket ticket) {
		ticketMapper.insert(ticket);
	}

	public void updateTicket(Ticket ticket) {
		ticketMapper.updateByPrimaryKey(ticket);
	}
}
