package yan.ibbie.TYcrowdfunding.potal.controller;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import yan.ibbie.TYcrowdfunding.bean.Cert;
import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.MemberCert;
import yan.ibbie.TYcrowdfunding.bean.Ticket;
import yan.ibbie.TYcrowdfunding.manager.service.CertService;
import yan.ibbie.TYcrowdfunding.manager.service.CerttypeService;
import yan.ibbie.TYcrowdfunding.potal.listener.PassListener;
import yan.ibbie.TYcrowdfunding.potal.listener.RefuseListener;
import yan.ibbie.TYcrowdfunding.potal.service.MemberService;
import yan.ibbie.TYcrowdfunding.potal.service.TicketService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Const;
import yan.ibbie.TYcrowdfunding.vo.Data;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	@Autowired
	TicketService ticketService;
	@Autowired
	CerttypeService certtypeService;
	@Autowired
	CertService certService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "member/index";
	}
	
	@RequestMapping("/apply")
	public String apply(HttpSession session) {
		Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		
		Ticket ticket = ticketService.selByMemberId(loginMember.getId());
		
		//判断申请走到哪一步骤，跳转到指定步骤
		if (ticket==null) {
			
			ticket = new Ticket();
			ticket.setMemberid(loginMember.getId());
			ticket.setStatus("0");
			ticketService.saveTicket(ticket);
			
		}else {
			if (ticket.getPstep().equals("accttype")) {
				return "redirect:/member/accttype.htm";
				
			}else if (ticket.getPstep().equals("basicinfo")) {
				return "redirect:/member/basicInfo.htm";
				
			}else if (ticket.getPstep().equals("uploadfile")) {
				return "redirect:/member/uploadFile.htm";
				
			}else if (ticket.getPstep().equals("checkmail")) {
				return "redirect:/member/checkMail.htm";
				
			}else if (ticket.getPstep().equals("checkauthcode")) {
				return "redirect:/member/checkAuthCode.htm";
				
			}else if (ticket.getPstep().equals("finishapply")) {
				return "redirect:/member/toIndex.htm";
				
			}
		}
		
		return "redirect:/member/accttype.htm";
		
	}
	
	@RequestMapping("/accttype")
	public String accttype() {
		return "member/accttype";
	}
	
	@RequestMapping("/basicInfo")
	public String basicInfo() {
		return "member/basicinfo";
	}
	
	@RequestMapping("/uploadFile")
	public String uploadFile(HttpSession session) {
		
		Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		List<Cert> certList = certtypeService.selectCertByAccttype(loginMember.getAccttype());
		session.setAttribute("certList", certList);
		
		return "member/uploadfile";
	}
	
	@RequestMapping("/checkMail")
	public String checkMail() {
		return "member/checkmail";
	}
	
	@RequestMapping("/checkAuthCode")
	public String checkAuthCode() {
		return "member/checkauthcode";
	}
	
	/*
	 * 更改用户类型
	 */
	@ResponseBody
	@RequestMapping("/updateAcctType")
	public Object updateAcctType(HttpSession session,String accttype) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			
			member.setAccttype(accttype);
			
			Integer count = memberService.updateAccttype(member);
			
			if (count==1) {
				
				Ticket ticket = ticketService.selByMemberId(member.getId());
				ticket.setPstep("basicinfo");
				ticketService.updateTicket(ticket);
				
				result.setSuccess(true);
				result.setMessage("操作成功！");
			}else {
				result.setSuccess(false);
				result.setMessage("操作失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("操作失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 更改基本信息
	 */
	@ResponseBody
	@RequestMapping("/updBasicInfo")
	public Object updBasicInfo(HttpSession session, Member member) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setCardnum(member.getCardnum());
			loginMember.setRealname(member.getRealname());
			loginMember.setTel(member.getTel());
			
			int count = memberService.updBasicInfo(loginMember);

			if (count==1) {
				
				Ticket ticket = ticketService.selByMemberId(loginMember.getId());
				ticket.setPstep("uploadfile");
				ticketService.updateTicket(ticket);
				
				result.setSuccess(true);
				result.setMessage("操作成功！");
			}else {
				result.setSuccess(false);
				result.setMessage("操作失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("操作失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 上传申请文件
	 */
	@ResponseBody
	@RequestMapping("/uploadCertFile")
	public Object uploadCertFile(HttpSession session,Data data) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			Integer memberid = member.getId();
			
			List<MemberCert> memberCerts = data.getMemberCerts();
			for (MemberCert memberCert : memberCerts) {
				MultipartFile imgFile = memberCert.getImgFile();
				
				//文件名
				String fileName = imgFile.getOriginalFilename();//全文件名，xxx.jpg
				String extName = fileName.substring(fileName.indexOf("."));//文件类型 .jpg
				String iconpath = UUID.randomUUID().toString()+extName;//生成随机文件名，adsdgh.jpg
				
				//储存路径
				String realPath = session.getServletContext().getRealPath("/pics");
				String path = realPath+"\\cert\\"+iconpath;
				
				imgFile.transferTo(new File(path));

				memberCert.setIconpath(iconpath);
				memberCert.setMemberid(memberid);
			}
			
			int count = certService.saveMemberCert(memberCerts);
			
			if (count==memberCerts.size()) {
				
				Ticket ticket = ticketService.selByMemberId(memberid);
				ticket.setPstep("checkemail");
				ticketService.updateTicket(ticket);
				
				result.setSuccess(true);
				result.setMessage("数据保存成功！");
			}else {
				result.setSuccess(false);
				result.setMessage("数据保存失败！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("操作失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 发送验证码
	 */
	@ResponseBody
	@RequestMapping("/sendCheckMail")
	public Object sendCheckMail(HttpSession session,String email) {
		
		AjaxResult result = new AjaxResult();
		Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		
		try {
			//如果用户使用的不是原来的邮箱，更新邮箱信息
			if (!member.getEmail().equals(email)) {
				member.setEmail(email);
				memberService.updateEmail(member);
			}
			//创建随机验证码
			StringBuffer authCode = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				Random random = new Random();
				authCode.append(random.nextInt(10));
			}
			//开启流程所需参数
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("authCode", authCode);
			variables.put("toEmail", email);
			variables.put("loginacct", member.getLoginacct());
			variables.put("passListener", new PassListener());
			variables.put("refuseListener", new RefuseListener());
			
			ProcessInstance processInstancep = runtimeService.startProcessInstanceByKey("auth", variables);
			
			Ticket ticket = ticketService.selByMemberId(member.getId());
			ticket.setPstep("checkauthcode");
			ticket.setPiid(processInstancep.getId());
			ticket.setAuthcode(authCode.toString());
			ticketService.updateTicket(ticket);
			
			result.setSuccess(true);
			result.setMessage("发送验证码成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("发送验证码失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 完成申请
	 */
	@ResponseBody
	@RequestMapping("/finishApply")
	public Object finishApply(HttpSession session,String inputAuthCode) {
		
		AjaxResult result = new AjaxResult();
		Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		Ticket ticket = ticketService.selByMemberId(member.getId());
		
		try {
			
			if (ticket.getAuthcode().equals(inputAuthCode)) {//如果输入的验证码正确
				Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(member.getLoginacct()).singleResult();
				taskService.complete(task.getId());
			
				ticket.setPstep("finishapply");
				ticketService.updateTicket(ticket);
				
				member.setAuthstatus("1");
				memberService.updateAuthStatus(member);
				
				result.setSuccess(true);
				result.setMessage("申请成功！");
			}else {
				result.setSuccess(false);
				result.setMessage("验证码有误，请重新输入！");
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("申请失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
}
