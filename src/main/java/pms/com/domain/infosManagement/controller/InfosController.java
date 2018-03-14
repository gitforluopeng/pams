package pms.com.domain.infosManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.infosManagement.model.Infos;
import pms.com.domain.infosManagement.services.InfosServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.PageUtil;

@Controller
public class InfosController {
	@Resource
	private InfosServiceInter infosServer;
	
	@RequestMapping(value="/userInfos_index",method=RequestMethod.GET)
	public String loadUserInfoIndex(){
		return "pms/infosManage/userInfos_index.jsp";
	}
	
	@RequestMapping(value="/myInfos_index",method=RequestMethod.GET)
	public String loadMyInfoIndex(){
		return "pms/infosManage/myInfos_index.jsp";
	}
	
	@RequestMapping(value="/infos_index",method=RequestMethod.GET)
	public String loadInfosIndex(){
		return "pms/infosManage/infos_index.jsp";
	}
	
	@RequestMapping(value="/infos_publish",method=RequestMethod.GET)
	public String infosPublish(){
		return "pms/infosManage/infos_pubish.jsp";
	}
	//http://localhost:8080/pams/infosManagement/load_infos?page=1&limit=10
	//http://localhost:8080/pams/infosManagement/load_infos?infos.userId=1&page=1&limit=10
	@RequestMapping(value="/load_infos",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadInfos(@BeanParam Infos infos,Integer page , Integer limit){
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
			Map<String, Object> map=infos.toMap();
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=infosServer.getInfosCount(map);
			List<Infos> datainfos=infosServer.getInfos(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", datainfos);
			return result;
		}
	}
	//http://localhost:8080/pams/infosManagement/add_infos?infos.userId=1&infos.userName=libo&infos.personName=李波&infos.unitId=2&infos.unitName=成都法院&infos.deptId=3&infos.deptName=法务部&infos.infoType=1&infos.infoContent=测试奖励
	@RequestMapping(value="/add_infos",method=RequestMethod.POST)
	@ResponseBody
	public String addInfo(@BeanParam Infos infos){
		JSONObject json = new JSONObject();
		int status=-1;
		status=infosServer.addInfos(infos);
		json.put("status", status);
		return json.toString();
	}
	//http://localhost:8080/pams/infosManagement/delete_infos?infos.id=2
	@RequestMapping(value="/delete_infos",method=RequestMethod.POST)
	@ResponseBody
	public String deleteInfo(@BeanParam Infos infos){
		JSONObject json = new JSONObject();
		int status=-1;
		status=infosServer.deleteInfos(infos);
		json.put("status", status);
		return json.toString();
	}
	/**
	 * 
	 *
	 * Task:只看我的请求路径
	 * @param infos
	 * @param page
	 * @param limit
	 * @return
	 * date: 2018年2月25日
	 * @author pengLuo
	 *
	 */
	//http://localhost:8080/pams/infosManagement/load_myInfos?page=1&limit=10
	@RequestMapping(value="/load_myInfos",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadMyInfos(@BeanParam Infos infos,Integer page , Integer limit){
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
			Map<String, Object> map=infos.toMap();
			map.put("userId", UserUtil.getShiroUser().getId());
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=infosServer.getInfosCount(map);
			List<Infos> datainfos=infosServer.getInfos(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", datainfos);
			return result;
		}
	}
}
