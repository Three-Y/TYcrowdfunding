package yan.ibbie.TYcrowdfunding.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Ticket;
import yan.ibbie.TYcrowdfunding.potal.service.MemberService;
import yan.ibbie.TYcrowdfunding.potal.service.TicketService;
import yan.ibbie.TYcrowdfunding.util.ApplicationContextUtils;

//审核通过后要执行的操作
public class PassListener implements ExecutionListener{

	/**
	 * 需要用到其它bean，但是此处不能自动装配，因为该类是自己创建的，不是容器创建的
	 * 不能自行创建ioc容器
	 * 使用自己写的ApplicationContextUtils工具类获取容器
	 * 该工具类实现了spring提供的ApplicationContextAware接口
	 * 该接口有一方法能传过来一个application对象
	 */
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		
		Integer memberId = (Integer)execution.getVariable("memberId");
		ApplicationContext applicationContext = ApplicationContextUtils.applicationContext;
		
		//更新t_member表信息
		MemberService memberService = applicationContext.getBean(MemberService.class);
		Member member = memberService.queryMemberById(memberId);
		member.setAuthstatus("2");
		memberService.updateAuthStatus(member);
		//更新t_ticket表信息
		TicketService ticketService = applicationContext.getBean(TicketService.class);
		Ticket ticket = ticketService.selByMemberId(memberId);
		ticket.setStatus("1");
		ticketService.updateTicket(ticket);
	}

}
