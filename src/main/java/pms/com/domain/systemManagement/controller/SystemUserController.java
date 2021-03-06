package pms.com.domain.systemManagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.systemManagement.model.MyShiroUserGroupRole;
import pms.com.domain.systemManagement.model.MyUser;
import pms.com.domain.systemManagement.model.OrganizeManagement;
import pms.com.domain.systemManagement.model.Station;
import pms.com.domain.systemManagement.model.SystemUser;
import pms.com.domain.systemManagement.services.OperationLogInter;
import pms.com.domain.systemManagement.services.SystemUserServiceInter;
import pms.com.domain.systemManagement.services.Imp.StationServer;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.model.ShiroUserGroupRole;
import pms.com.system.shiro.util.UserUtil;


@Controller
public class SystemUserController {
	
	private static String baseUrl = "pms/systemUserManagement/";
	@Resource
	private SystemUserServiceInter systemUserServer;
	@Resource
	private OperationLogInter operationLogInter;
	@Resource
	private StationServer stationServer;
	
	@RequestMapping(value="/user_index",method=RequestMethod.GET)
	public String loadUserIndex(){
		return "pms/systemUserManagement/user_index.jsp";
	}
	
	@RequestMapping(value="/user_add",method=RequestMethod.GET)
	public String loadUserAdd(){
		return baseUrl+"user_add.jsp";
	}
	
	@RequestMapping(value="/user_edit",method=RequestMethod.GET)
	public String loadUserEdit(){
		return baseUrl+"user_edit.jsp";
	}
	
	@RequestMapping(value="/load_all_units",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadAllUnits(){
		Map<String, Object> result = new HashMap<String, Object>();
		List <OrganizeManagement> organizeManagements=systemUserServer.getAllUnitList();
		result.put("units", organizeManagements);
		return result;
	}
	
	@RequestMapping(value="/load_all_stations",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadAllStations(){
		Map<String, Object> result = new HashMap<String, Object>();
		List <Station> stations=stationServer.getStation(null);
		result.put("stations", stations);
		return result;
	}
	
	@RequestMapping(value="/load_unit_depts",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadUnitDepts(Long unitId){
		Map<String, Object> result = new HashMap<String, Object>();
		List <OrganizeManagement> organizeManagements=systemUserServer.getDeptByUnitId(unitId);
		result.put("depts", organizeManagements);
		return result;
	}
	
	@RequestMapping(value="/load_shiro_roles",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadShiroRoles(Long deptId){
		Map<String, Object> result = new HashMap<String, Object>();
		Set<ShiroUserGroupRole> shiroUserGroupRoles=systemUserServer.getShiroUserGroupRoleById(deptId);
		if (shiroUserGroupRoles!=null&&(!shiroUserGroupRoles.isEmpty())) {
			List<Long> shiroRoleIds=new ArrayList<Long>();
			for (ShiroUserGroupRole shiroUserGroupRole : shiroUserGroupRoles) {
				shiroRoleIds.add(shiroUserGroupRole.getShiroRoleId());
			}
			if (shiroRoleIds.isEmpty()) {
				return null;
			}
			Set<ShiroRole> shiroRoles=systemUserServer.getShiroRolesByIds(shiroRoleIds);
			result.put("roles", shiroRoles);
			return result;
		}
		else {
			return null;
		}
	}
	
	@RequestMapping(value="/add_user",method=RequestMethod.POST)
	@ResponseBody
	public String addUser(@BeanParam ShiroUser shiroUser,String password,@BeanParam SystemUser systemUser,@BeanParam MyShiroUserGroupRole myShiroUserGroupRole,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systemUserServer.addSystemUser(shiroUser, password, systemUser, myShiroUserGroupRole);
		json.put("status", status);
		if(1 == status){
			String userNameString = shiroUser.getUsername();
			StringBuilder record = new StringBuilder("添加用户:");
			record.append(userNameString);
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/load_user_infos",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadUserInfos(Integer page , Integer limit){
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
			int count=systemUserServer.getUserCount();
			List<MyUser> myUsers=systemUserServer.getUserForPage(page-1, limit);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", myUsers);
			return result;
		}	
	}
	
	@RequestMapping(value="/update_user",method=RequestMethod.POST)
	@ResponseBody
	public String updateUser(@BeanParam ShiroUser shiroUser,String unEditUsername,String password,@BeanParam SystemUser systemUser,@BeanParam MyShiroUserGroupRole myShiroUserGroupRole,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systemUserServer.updateSystemUser(shiroUser, unEditUsername, password, systemUser, myShiroUserGroupRole);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("编辑用户:");
			record.append(unEditUsername);
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/update_user_islock",method=RequestMethod.POST)
	@ResponseBody
	public String updateUserIsLock(Long id,boolean accountLockStatus,String username,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=0;
		ShiroUser shiroUser=new ShiroUser();
		shiroUser.setId(id);
		shiroUser.setAccountLockStatus(accountLockStatus);
		status=systemUserServer.updateUserIsLock(shiroUser);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("将用户:");
			record.append(username);
			record.append("设置为");
			String isLockString = accountLockStatus?"停用":"启用";
			record.append(isLockString);
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/delete_user",method=RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@BeanParam ShiroUser shiroUser,@BeanParam SystemUser systemUser,@BeanParam MyShiroUserGroupRole myShiroUserGroupRole,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systemUserServer.deleteSystemUser(shiroUser, systemUser, myShiroUserGroupRole);
		json.put("status", status);
		if(1 == status){
			String userNameString = shiroUser.getUsername();
			StringBuilder record = new StringBuilder("删除用户:");
			record.append(userNameString);
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
	
	@RequestMapping(value="/query_user_infos",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserInfos(String name, Long unitId, Long deptId, Integer page, Integer limit){
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
			if (("".equals(name)||name==null)&&(unitId==0||unitId==null)&&(deptId==0||deptId==null)) {
				int count=systemUserServer.getUserCount();
				List<MyUser> myUsers=systemUserServer.getUserForPage(page-1, limit);
				result.put("code", 0);
				result.put("msg", "请求所有数据成功");
				result.put("count", count);
				result.put("data", myUsers);
				return result;
			}
			else if (("".equals(name)||name==null)&&(unitId!=0&&unitId!=null)&&(deptId==0||deptId==null)) {
				List<MyUser> myUsers=systemUserServer.getUserForPageByUnitId(unitId, page-1, limit);
				int count=systemUserServer.getUserCountByUnitId(unitId);
				result.put("code", 0);
				result.put("msg", "通过单位请求数据成功");
				result.put("count", count);
				result.put("data", myUsers);
				return result;
			}
			else if (("".equals(name)||name==null)&&(unitId!=0&&unitId!=null)&&(deptId!=0&&deptId!=null)) {
				List<MyUser> myUsers=systemUserServer.getUserForPageByDeptId(unitId, deptId, page-1, limit);
				int count=systemUserServer.getUserCountByDeptId(unitId, deptId);
				result.put("code", 0);
				result.put("msg", "通过部门请求数据成功");
				result.put("count", count);
				result.put("data", myUsers);
				return result;
			}
			else if ((!("".equals(name)||name==null))&&(unitId==0||unitId==null)&&(deptId==0||deptId==null)) {
				List<MyUser> myUsers=systemUserServer.getUserForPageByName(name, page-1, limit);
				int count=systemUserServer.getUserCountByName(name);
				result.put("code", 0);
				result.put("msg", "通过名字请求数据成功");
				result.put("count", count);
				result.put("data", myUsers);
				return result;
			}
			else if ((!("".equals(name)||name==null))&&(unitId!=0&&unitId!=null)&&(deptId==0||deptId==null)) {
				List<MyUser> myUsers=systemUserServer.getUserForPageByNameAndUId(name, unitId, page-1, limit);
				int count=systemUserServer.getUserCountByNameAndUId(name, unitId);
				result.put("code", 0);
				result.put("msg", "通过名字及单位请求数据成功");
				result.put("count", count);
				result.put("data", myUsers);
				return result;
			}
			else {
				List<MyUser> myUsers=systemUserServer.getUserForPageByAllS(name, unitId, deptId, page-1, limit);
				int count=systemUserServer.getUserCountByAllS(name, unitId, deptId);
				result.put("code", 0);
				result.put("msg", "通过名字及单位请求数据成功");
				result.put("count", count);
				result.put("data", myUsers);
				return result;
			}
		}	
	}
	
	//普通用户系统请求我的信息页面映射
	@RequestMapping(value="/myuser_index",method=RequestMethod.GET)
	public String loadMyUserIndex(){
		return baseUrl+"myuser_index.jsp";
	}
	
	//普通用户编辑我的信息页面映射
	@RequestMapping(value="/myuser_edit",method=RequestMethod.GET)
	public String loadMyUserEdit(){
		return baseUrl+"myuser_edit.jsp";
	}
	
	//普通用户请求我的信息的数据
	@RequestMapping(value="/load_myuser_infos",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadMyUserInfos(){
		Map<String, Object> result = new HashMap<String, Object>();
		MyUser myUser=systemUserServer.getUserByShiroUserId(UserUtil.getShiroUser().getId());
		result.put("data", myUser);
		return result;
	}
	//普通用户修改我的信息
	@RequestMapping(value="/update_myuser",method=RequestMethod.POST)
	@ResponseBody
	public String updateMyUser(@BeanParam ShiroUser shiroUser,String unEditUsername,String password,@BeanParam SystemUser systemUser,@BeanParam MyShiroUserGroupRole myShiroUserGroupRole,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int status=-1;
		status=systemUserServer.updateSystemUser(shiroUser, unEditUsername, password, systemUser, myShiroUserGroupRole);
		json.put("status", status);
		if(1 == status){
			StringBuilder record = new StringBuilder("编辑用户:");
			record.append(unEditUsername);
			operationLogInter.insertOperationLog(record.toString(), request);
		}
		return json.toString();	
	}
}
