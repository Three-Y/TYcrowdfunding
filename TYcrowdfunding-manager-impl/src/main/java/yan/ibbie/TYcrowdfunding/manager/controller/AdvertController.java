package yan.ibbie.TYcrowdfunding.manager.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import yan.ibbie.TYcrowdfunding.bean.Advertisement;
import yan.ibbie.TYcrowdfunding.bean.User;
import yan.ibbie.TYcrowdfunding.manager.service.AdvertService;
import yan.ibbie.TYcrowdfunding.util.AjaxResult;
import yan.ibbie.TYcrowdfunding.util.Const;
import yan.ibbie.TYcrowdfunding.util.Page;
import yan.ibbie.TYcrowdfunding.util.StringUtil;

@Controller
@RequestMapping("/advert")
public class AdvertController {
	
	@Autowired
	AdvertService advertService;

	@RequestMapping("/index")
	public String index() {
		return "advert/index";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "advert/add";
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(Advertisement advertisement,HttpSession session,HttpServletRequest request) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			/*
			 * 当请求表单是enctype="multipart/form-data"
			 * 则框架会将请求转换成MultipartHttpServletRequest
			 * 上传的文件放在了MultipartHttpServletRequest中
			 */
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)request;
			MultipartFile mfile = mRequest.getFile("advpic");
			
			//文件名
			String fileName = mfile.getOriginalFilename();//全文件名，xxx.jpg
			String extName = fileName.substring(fileName.indexOf("."));//文件类型 .jpg
			String iconpath = UUID.randomUUID().toString()+extName;//生成随机文件名，adsdgh.jpg
			
			//存储路径
			ServletContext servletContext = session.getServletContext();
			String realPath = servletContext.getRealPath("/pics");
			String path = realPath+"\\adv\\"+iconpath;
			
			//将文件保存到硬盘中
			mfile.transferTo(new File(path));
			
			//取用户的id
			User user = (User)session.getAttribute(Const.LOGIN_USER);
			Integer userid = user.getId();
			
			//将数据封装到advertisement中
			advertisement.setIconpath(iconpath);
			advertisement.setUserid(userid);
			advertisement.setStatus("1");//0 - 草稿， 1 - 未审核， 2 - 审核完成， 3 - 发布
			
			//保存数据到数据库中
			Integer count = advertService.saveAdvert(advertisement);
			
			result.setSuccess(count==1);
			result.setMessage("添加数据成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("添加数据失败！");
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doData")
	public Object doData(
			@RequestParam(defaultValue="1")int pageno,
			@RequestParam(defaultValue="10")int pageSize,
			String queryText) {
		
		AjaxResult result = new AjaxResult();
		
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("pageno", pageno);
		paramMap.put("pageSize", pageSize);
		
		if (StringUtil.isNotEmpty(queryText)) {
			paramMap.put("queryText", queryText);
		}
		
		try {
			
			Page page = advertService.queryPage(paramMap);
			
			result.setPage(page);
			result.setSuccess(true);
			result.setMessage("加载数据成功！");
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("加载数据失败！");
			e.printStackTrace();
		}
		
		
		return result;
	}
}
