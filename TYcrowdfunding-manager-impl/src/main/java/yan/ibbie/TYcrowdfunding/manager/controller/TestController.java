package yan.ibbie.TYcrowdfunding.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import yan.ibbie.TYcrowdfunding.manager.service.TestService;

@Controller
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private ApplicationContext ac;
	@RequestMapping("/test")
	public String test() {
		
		String[] beans = ac.getBeanDefinitionNames();
		for (String bean : beans) {
			System.out.println(bean);
		}
		
		System.out.println("TestController");
		testService.insert();
		return "success";
	}
}
