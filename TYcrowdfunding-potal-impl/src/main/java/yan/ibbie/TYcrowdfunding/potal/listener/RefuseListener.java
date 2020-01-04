package yan.ibbie.TYcrowdfunding.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Ticket;
import yan.ibbie.TYcrowdfunding.potal.service.MemberService;
import yan.ibbie.TYcrowdfunding.potal.service.TicketService;
import yan.ibbie.TYcrowdfunding.util.ApplicationContextUtils;

//审核拒绝后要执行的操作
public class RefuseListener implements ExecutionListener{

	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		
		Integer memberId = (Integer)execution.getVariable("memberId");
		ApplicationContext applicationContext = ApplicationContextUtils.applicationContext;
		
		//更新t_member表信息
		MemberService memberService = applicationContext.getBean(MemberService.class);
		Member member = memberService.queryMemberById(memberId);
		member.setAuthstatus("0");
		memberService.updateAuthStatus(member);
		//更新t_ticket表信息
		TicketService ticketService = applicationContext.getBean(TicketService.class);
		Ticket ticket = ticketService.selByMemberId(memberId);
		ticket.setStatus("1");
		ticketService.updateTicket(ticket);
		
	}

}
