package pms.com.domain.systemManagement.controller;

import java.util.ArrayList;
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
import pms.com.domain.systemManagement.services.SystemRoleServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroResourcesRoles;
import pms.com.system.shiro.model.ShiroRole;

@Controller
public class SystemRoleController {
	
	@Resource
	private SystemRoleServiceInter systerRoleServer;

	
	@RequestMapping(value="/systemrole_index",method=RequestMethod.GET)
	public String loadRoleIndex(){
		return "pms/systemRole/system_role.jsp";
	}
	
	@RequestMapping(value="/load_system_roles",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadRoleInfos(Integer page , Integer limit){
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
			int count=systerRoleServer.getAllRoleCount();
			List<ShiroRole> shiroRoles=systerRoleServer.getAllRoleForPage(page-1, limit);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", shiroRoles);
			return result;
		}	
	}
	
	@RequestMapping(value="/add_system_role",method=RequestMethod.POST)
	@ResponseBody
	public String addSystemRole(@BeanParam ShiroRole shiroRole,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerRoleServer.addRole(shiroRole);
		json.put("status", status);
		return json.toString();	
	}
	
	@RequestMapping(value="/update_system_role",method=RequestMethod.POST)
	@ResponseBody
	public String updateSystemRole(@BeanParam ShiroRole shiroRole,String unEditName,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerRoleServer.updateRole(shiroRole,unEditName);
		json.put("status", status);
		return json.toString();	
	}
	
	@RequestMapping(value="/update_system_role_isuse",method=RequestMethod.POST)
	@ResponseBody
	public String updateSystemRoleIsUse(Long id,String name,boolean lockStatus,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		ShiroRole shiroRole=new ShiroRole();
		shiroRole.setId(id);
		shiroRole.setName(name);
		shiroRole.setLockStatus(lockStatus);
		status=systerRoleServer.updateRoleIsUse(shiroRole);
		json.put("status", status);
		return json.toString();	
	}
	
	@RequestMapping(value="/delete_system_role",method=RequestMethod.POST)
	@ResponseBody
	public String deleteSystemRole(@BeanParam ShiroRole shiroRole,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerRoleServer.deleteRole(shiroRole);
		json.put("status", status);
		return json.toString();	
	}
	
	@RequestMapping(value="/load_all_resources",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadAllRes(Long id){
		Map<String, Object> result = new HashMap<String, Object>();
		List <ShiroResources> shiroResources=systerRoleServer.loadAllShiroResources();
		result.put("code", 0);
		result.put("msg", "请求数据成功");
		result.put("count", shiroResources.size());
		result.put("data", shiroResources);
		return result;
	}
	@RequestMapping(value="/load_notadd_resources",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadNotAddRes(Long id){
		Map<String, Object> result = new HashMap<String, Object>();
		List <ShiroResources> shiroResourceAll=systerRoleServer.loadAllShiroResources();
		if (shiroResourceAll==null||shiroResourceAll.isEmpty()) {
			result.put("code", 0);
			result.put("msg", "无数据");
			return result;
		}
		List <ShiroResources> shiroResources=new ArrayList<ShiroResources>();
		for (ShiroResources shiroResource : shiroResourceAll) {
			System.out.println(shiroResource.getShiroRoleId());
			shiroResource.setShiroRoleId(id);
			shiroResources.add(shiroResource);
		}
		List <ShiroResources> itshiroResources=systerRoleServer.loadAllByShiroRoleId(id);
		shiroResources.removeAll(itshiroResources);
		result.put("code", 0);
		result.put("msg", "请求数据成功");
		result.put("count", shiroResources.size());
		result.put("data", shiroResources);
		return result;
	}
	@RequestMapping(value="/load_it_resources",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> moreRole(Long id){
		Map<String, Object> result = new HashMap<String, Object>();
		List <ShiroResources> shiroResources=systerRoleServer.loadAllByShiroRoleId(id);
		int count=0;
		if (shiroResources!=null&&!shiroResources.isEmpty()) {
			count=shiroResources.size();
		}
		result.put("code", 0);
		result.put("msg", "请求数据成功");
		result.put("count", count);
		result.put("data", shiroResources);
		return result;
	}
	@RequestMapping(value="/add_role_resource",method=RequestMethod.POST)
	@ResponseBody
	public String addRoleReSource(@BeanParam ShiroResourcesRoles shiroResourcesRoles,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerRoleServer.addResouceRole(shiroResourcesRoles);
		json.put("status", status);
		return json.toString();	
	}
	@RequestMapping(value="/delete_role_resource",method=RequestMethod.POST)
	@ResponseBody
	public String deleteRoleReSource(@BeanParam ShiroResourcesRoles shiroResourcesRoles,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systerRoleServer.deleteResouceRole(shiroResourcesRoles);
		json.put("status", status);
		return json.toString();	
	}
}
