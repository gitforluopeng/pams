package pms.com.domain.systemManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pms.com.domain.systemManagement.model.SystemName;
import pms.com.domain.systemManagement.services.OperationLogInter;
import pms.com.domain.systemManagement.services.SystemNameServiceInter;
import pms.com.system.shiro.annotate.BeanParam;


@Controller
public class SystemNameController {
	
	@Resource
	private SystemNameServiceInter systerNameserver;
	@Resource
	private OperationLogInter operationLogInter;
	
	@RequestMapping(value="/systemname_index",method=RequestMethod.GET)
	public String loadNameIndex(){
		return "pms/systemName/system_name.jsp";
	}
	
	@RequestMapping(value="/load_system_names",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadNameInfos(Integer page , Integer limit){
		Map<String, Object> result = new HashMap<String, Object>();
		if(page == null || limit == null) {
			result.put("code", 0);
			result.put("msg", "页数错误，请求数据失败");
			return result;
		}
		else if(limit==0) {
			result.put("code", 0);
			result.put("msg", "分页大小错误，请求数据失败");
			return result;
		}
		else {
			int count=systerNameserver.getSystemNameCount();
			List<SystemName> systemNames=systerNameserver.getAllSystemName(page-1, limit);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", systemNames);
			return result;
		}	
	}
	
	@RequestMapping(value="/add_system_name",method=RequestMethod.POST)
	@ResponseBody
	public String addSystemName(@BeanParam SystemName systemName,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerNameserver.addSystemName(systemName);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("新增系统名称:");
			record.append(systemName.getName());
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/update_system_name",method=RequestMethod.POST)
	@ResponseBody
	public String updateSystemName(@BeanParam SystemName systemName,String unEditName,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerNameserver.updateSystemName(systemName,unEditName);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("编辑系统名称: '");
			record.append(unEditName);
			record.append("'  为: '");
			record.append(systemName.getName());
			record.append("'");
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/update_system_name_isuse",method=RequestMethod.POST)
	@ResponseBody
	public String updateSystemNameIsUse(@BeanParam SystemName systemName,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerNameserver.updateSystemIsUse(systemName);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("设置系统名称:");
			record.append(systemName.getName());
			record.append(systemName.getIsUse()?"启用":"停用");
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/delete_system_name",method=RequestMethod.POST)
	@ResponseBody
	public String deleteSystemName(@BeanParam SystemName systemName,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerNameserver.deleteSystemName(systemName);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("删除系统名称:");
			record.append(systemName.getName());
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
}
