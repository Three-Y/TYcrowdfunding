package ActivitiTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yan.ibbie.TYcrowdfunding.activiti.listener.NoListener;
import yan.ibbie.TYcrowdfunding.activiti.listener.YesListener;

public class TestActiviti {
	
	ApplicationContext app = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	ProcessEngine processEngine = (ProcessEngine)app.getBean("processEngine");//流程引擎
	
	//7.查看历史数据
	@Test
	public void test7() {
		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstanceQuery hpiq = historyService.createHistoricProcessInstanceQuery();
		String id = hpiq.processInstanceId("106").singleResult().getId();
		System.out.println("HistoricProcessInstanceId="+id);
	}
	
	//6.领取任务(taskService)
	@Test
	public void test6() {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> list = taskQuery.taskCandidateGroup("tl").list();
		long count = taskQuery.taskAssignee("zhangsan").count();
		System.out.println("张三的任务数："+count);
		for (Task task : list) {
			taskService.claim(task.getId(), "zhangsan");//将任务分配给zhangsan
		}
		count = taskQuery.taskAssignee("zhangsan").count();
		System.out.println("领取后，张三的任务数："+count);
	}
	
	//5.完成任务(taskService)
	@Test
	public void test5() {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		long count = taskQuery.taskAssignee("zhangsan").count();
//		System.out.println("领取后，张三的任务数："+count);
		List<Task> list = taskQuery.taskAssignee("zhangsan").processInstanceId("1701").list();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("flag", true);
		for (Task task : list) {
			taskService.setVariable(task.getId(), "flag", false);
//			taskService.complete(task.getId(), map);
			taskService.complete(task.getId());
		}
	}
	
	//4.创建流程实例(RuntimeService)
		/*
		 * act_hi_actinst, 历史的活动的任务表.
		 * act_hi_procinst, 历史的流程实例表.
		 * act_hi_taskinst, 历史的流程任务表
		 * act_ru_execution, 正在运行的任务表.
		 * act_ru_task, 运行的任务数据表.
		 */
	@Test
	public void test4() {
		//查询出一个流程定义
		ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().processDefinitionKey("mail").singleResult();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		//假如流程有流程变量，开启流程实例要传入参数（例：myProcess7）
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("yesListener",new YesListener());
//		map.put("noListener",new NoListener());
		
		//根据流程定义的id开启一个流程实例
//		ProcessInstance processInstance1 = runtimeService.startProcessInstanceById(pd.getId(),map);
		ProcessInstance processInstance2 = runtimeService.startProcessInstanceById(pd.getId());
//		System.out.println("processInstance1="+processInstance1);
		System.out.println("processInstance2="+processInstance2);
		
	}
	
	//3.查询部署流程定义的信息(RepositoryService,DeploymentQuery)
	@Test
	public void test3() {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		/*
		 * 想查什么就创建什么查询对象
		 */
		//查询流程部署
		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
		//查询流程定义
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		
		//list()方法，查询出所有流程定义
		List<ProcessDefinition> list = processDefinitionQuery.list();
		//count()方法，查询出一共有多少条流程定义
		long count = processDefinitionQuery.count();
		System.out.println("一共有"+count+"条流程定义");
		
		
		for (ProcessDefinition processDefinition : list) {
			System.out.println("--------------------------------------------");
			System.out.println("流程定义的id="+processDefinition.getId());
			System.out.println("流程定义的key="+processDefinition.getKey());
			System.out.println("流程定义的name="+processDefinition.getName());
			System.out.println("流程定义的version="+processDefinition.getVersion());
		}
		/*
		 * 其中有一条sql语句：
		 * select distinct RES.* from ACT_RE_PROCDEF RES order by RES.ID_ asc LIMIT ? OFFSET ? 
		 * LIMIT 5 OFFSET 3，表示从索引为3的开始，取5条数据
		 * 而以前的：LIMIT 5 , 3 ，表示从索引为5的开始，取3条数据
		 */
		
		//查询最新版本的流程定义
		List<ProcessDefinition> list2 = processDefinitionQuery.latestVersion().list();
		for (ProcessDefinition processDefinition : list2) {
			System.out.println("************************************************");
			System.out.println("最新的流程定义的id="+processDefinition.getId());
			System.out.println("最新的流程定义的key="+processDefinition.getKey());
			System.out.println("最新的流程定义的name="+processDefinition.getName());
			System.out.println("最新的流程定义的version="+processDefinition.getVersion());
		}
		
		//排序查询，分页查询
		//注意，这个方法是指，查询出每个流程最新版本，然后按照版本号排序
		List<ProcessDefinition> list3 = processDefinitionQuery.orderByProcessDefinitionVersion().desc().listPage(0, 2);
		for (ProcessDefinition pd : list3) {
			System.out.println("------------排序查询，分页查询-------------");
			System.out.println("流程定义的id="+pd.getId());
			System.out.println("流程定义的key="+pd.getKey());
			System.out.println("流程定义的name="+pd.getName());
			System.out.println("流程定义的version="+pd.getVersion());
		}
		
		//根据key（也就是myProcess）查询流程定义，也只会查出最新版本
		ProcessDefinition pd = processDefinitionQuery.processDefinitionKey("myProcess3").singleResult();
		System.out.println("------------根据key查询流程定义-------------");
		System.out.println("流程定义的id="+pd.getId());
		System.out.println("流程定义的key="+pd.getKey());
		System.out.println("流程定义的name="+pd.getName());
		System.out.println("流程定义的version="+pd.getVersion());
	}

	//2.部署流程定义
	@Test
	public void test2() {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		Deployment deploy1 = repositoryService.createDeployment().addClasspathResource("TestMail.bpmn").deploy();
		Deployment deploy2 = repositoryService.createDeployment().addClasspathResource("baohan.bpmn").deploy();
		Deployment deploy3 = repositoryService.createDeployment().addClasspathResource("bingxing.bpmn").deploy();
		Deployment deploy4 = repositoryService.createDeployment().addClasspathResource("conditions.bpmn").deploy();
		Deployment deploy5 = repositoryService.createDeployment().addClasspathResource("group.bpmn").deploy();
		Deployment deploy6 = repositoryService.createDeployment().addClasspathResource("huchi.bpmn").deploy();
		Deployment deploy7 = repositoryService.createDeployment().addClasspathResource("simple.bpmn").deploy();
		
		System.out.println("Deployment1="+deploy1);
		System.out.println("Deployment2="+deploy2);
		System.out.println("Deployment3="+deploy3);
		System.out.println("Deployment4="+deploy4);
		System.out.println("Deployment5="+deploy5);
		System.out.println("Deployment6="+deploy6);
		System.out.println("Deployment7="+deploy7);
	}
	
	//1.创建流程引擎，创建23张表格
	@Test
	public void testCteateTable() {//自动创建表格
		
		System.out.println("ProcessEngine:"+processEngine);
		
	}
}
