package yan.ibbie.TYcrowdfunding.manager.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Page;

@Controller
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
	RepositoryService repositoryService;
	
	@RequestMapping("/index")
	public String index() {
		return "process/index";
	}
	
	@RequestMapping("/showImg")
	public String showImg() {
		return "process/showimg";
	}
	
	@ResponseBody
	@RequestMapping("/doData")
	public Object doData(@RequestParam(value="pageno",required=false,defaultValue="1")Integer pageno,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			Page page = new Page(pageno, pageSize);
			
			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
			
			Long count = processDefinitionQuery.count();
			page.setTotalSize(count.intValue());
			
			//由于自关联问题，搜出来的结果无法转换成json串，需要自行再封装一下
			List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pageSize);
			List<Map<String, Object>> myListPage = new ArrayList<Map<String,Object>>();
			for (ProcessDefinition processDefinition : listPage) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",processDefinition.getId());
				map.put("name",processDefinition.getName());
				map.put("version",processDefinition.getVersion());
				map.put("key",processDefinition.getKey());
				myListPage.add(map);
			}
			
			page.setDatas(myListPage);
			
			result.setPage(page);
			result.setSuccess(true);
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("加载数据失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deploy")
	public Object deploy(HttpServletRequest request) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			MultipartHttpServletRequest mhsRequest = (MultipartHttpServletRequest) request;
			
			MultipartFile file = mhsRequest.getFile("deployFile");
			
			repositoryService.createDeployment()
				.addInputStream(file.getOriginalFilename(), file.getInputStream())
				.deploy();
			
			result.setSuccess(true);
			result.setMessage("部署成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("部署失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(String id) {//注意，传过来的是流程定义id
		
		AjaxResult result = new AjaxResult();
		
		try {
			//删除流程定义要传的参数是流程部署id
			ProcessDefinition processDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
			repositoryService.deleteDeployment(processDef.getDeploymentId(), true);//true表示将关联的表数据也删除
			
			result.setSuccess(true);
			result.setMessage("删除成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doShowImg")
	public void doShowImg(String id,HttpServletResponse response) {//注意，传过来的是流程定义id
		
		try {
			
			ProcessDefinition processDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
			
			//根据部署id和资源名称查找资源，以流的形式返回
			InputStream imgAsStream = repositoryService.getResourceAsStream(
					processDef.getDeploymentId(), processDef.getDiagramResourceName());
			
			ServletOutputStream outputStream = response.getOutputStream();
			
			IOUtils.copy(imgAsStream, outputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
