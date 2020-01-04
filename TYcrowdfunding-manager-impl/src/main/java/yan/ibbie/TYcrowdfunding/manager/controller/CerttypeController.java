package yan.ibbie.TYcrowdfunding.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yan.ibbie.TYcrowdfunding.bean.AccountTypeCert;
import yan.ibbie.TYcrowdfunding.bean.Cert;
import yan.ibbie.TYcrowdfunding.manager.service.CertService;
import yan.ibbie.TYcrowdfunding.manager.service.CerttypeService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;

@Controller
@RequestMapping("/certtype")
public class CerttypeController {
	
	@Autowired
	CerttypeService certtypeService;
	
	@Autowired
	CertService certService;

	@RequestMapping("/index")
	public String index(Map<String, Object> map) {
		
		List<Cert> allCert = certService.selectAll();
		map.put("allCert", allCert);
		
		List<AccountTypeCert> allAccttypeCert = certtypeService.selectAll();
		map.put("allAccttypeCert", allAccttypeCert);
		
		
		return "certtype/index";
	}
	
	@ResponseBody
	@RequestMapping("/insertAccttypeCert")
	public Object insertAccttypeCert(AccountTypeCert accountTypeCert) {
		AjaxResult result = new AjaxResult();
		try {
			
			int count = certtypeService.insertAccttypeCert(accountTypeCert);
			
			if (count==1) {
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("分类关系保存失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deleteAccttypeCert")
	public Object deleteAccttypeCert(AccountTypeCert accountTypeCert) {
		AjaxResult result = new AjaxResult();
		try {
			
			int count = certtypeService.deleteAccttypeCert(accountTypeCert);
			
			if (count==1) {
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("分类关系保存失败！");
			e.printStackTrace();
		}
		return result;
	}
}
