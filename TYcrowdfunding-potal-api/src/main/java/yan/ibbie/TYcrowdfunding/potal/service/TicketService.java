package yan.ibbie.TYcrowdfunding.potal.service;

import yan.ibbie.TYcrowdfunding.bean.Ticket;

public interface TicketService {

	Ticket selByMemberId(Integer id);

	void saveTicket(Ticket ticket);

	void updateTicket(Ticket ticket);

}
