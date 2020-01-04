package yan.ibbie.TYcrowdfunding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yan.ibbie.TYcrowdfunding.bean.Member;
import yan.ibbie.TYcrowdfunding.bean.Ticket;
import yan.ibbie.TYcrowdfunding.manager.service.AuthcertService;
import yan.ibbie.TYcrowdfunding.potal.service.MemberService;
import yan.ibbie.TYcrowdfunding.potal.service.TicketService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Page;
import yan.ibbie.TYcrowdfunding.vo.Data;

@Controller
@RequestMapping("/auth_cert")
public class AuthcertController {
	
	@Autowired
	TaskService taskService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	AuthcertService authcertService;
	@Autowired
	MemberService memberService;
	@Autowired
	TicketService ticketService;
	
	@RequestMapping("/index")
	public String index() {
		return "auth_cert/index";
	}
	
	@RequestMapping("/show")
	public String show(Integer memberId,Map<String, Object> map) {
		/*
		 * 用memberid查出：真实姓名，身份证号，电话，认证资料，certid
		 * 用certid查出：认证资料对应的名称
		 */
		Member member = memberService.queryMemberById(memberId);
		
		List<Map<String, Object>> certMapList = memberService.queryCertByMemberId(memberId);
		
		map.put("member", member);
		map.put("certInfo", certMapList);
		
		return "auth_cert/show";
	}
	
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object doIndex(
			@RequestParam(value = "pageno",defaultValue = "1",required = false)int pageno,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize) {
		AjaxResult result = new AjaxResult();
		try {
			
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("auth").taskCandidateGroup("backUsers");
			Page page = new Page(pageno, pageSize);
			List<Map<String, Object>> taskMapList = new ArrayList<Map<String,Object>>();
			
			long count = taskQuery.count();
			page.setTotalSize(Integer.parseInt(String.valueOf(count)));
			
			int startIndex = page.getStartIndex();
			List<Task> taskList = taskQuery.listPage(startIndex, pageSize);
			
			for (Task task : taskList) {
				HashMap<String, Object> map = new HashMap<String,Object>();
				map.put("id",task.getId() );
				map.put("name", task.getName());
				//查询流程
				ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
				map.put("procDefName", procDef.getName());
				//查询申请人
				Ticket ticket = authcertService.queryTicketByPiid(task.getProcessInstanceId());
				Member member = authcertService.queryMemberByTicket(ticket);
				map.put("memberName", member.getUsername());
				map.put("memberId", member.getId());
				taskMapList.add(map);
			}
			
			page.setDatas(taskMapList);
			result.setPage(page);;
			result.setSuccess(true);
			result.setMessage("加载数据成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("加载数据失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/applyPass")
	public Object applyPass(Integer memberId,String taskId) {
		AjaxResult result = new AjaxResult();
		try {
			
			//完成task
			taskService.setVariable(taskId, "flag", true);
			taskService.setVariable(taskId, "memberId", memberId);
			taskService.complete(taskId);
			
			result.setSuccess(true);
			result.setMessage("操作成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("操作失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/applyRefuse")
	public Object applyRefuse(Integer memberId,String taskId) {
		AjaxResult result = new AjaxResult();
		try {
			
			//完成task
			taskService.setVariable(taskId, "flag", false);
			taskService.setVariable(taskId, "memberId", memberId);
			taskService.complete(taskId);
			
			result.setSuccess(true);
			result.setMessage("操作成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("操作失败！");
			e.printStackTrace();
		}
		return result;
	}
}
