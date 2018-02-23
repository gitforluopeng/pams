package pms.com.domain.trainingPlan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.systemManagement.model.MyUser;
import pms.com.domain.systemManagement.services.SystemUserServiceInter;
import pms.com.domain.trainingPlan.model.TrainPlan;
import pms.com.domain.trainingPlan.services.TrainPlanServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.PageUtil;

@Controller
public class TrainPlanController {
	@Resource
	private TrainPlanServiceInter trainPlanServer;
	@Resource
	private SystemUserServiceInter systemUserServer;
	
	@RequestMapping(value="/trainPlan_index",method=RequestMethod.GET)
	public String loadTrainPlanIndex(){
		return "pms/trainPlanManage/trainPlan_index.jsp";
	}
	
	@RequestMapping(value="/trainPlan_add",method=RequestMethod.GET)
	public String loadTrainPlanAddPage(){
		return "pms/trainPlanManage/trainPlan_add.jsp";
	}
	
	@RequestMapping(value="/load_trainPlans",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadTrainPlans(@BeanParam TrainPlan trainPlan , Integer page , Integer limit){
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
			Map<String, Object> map=trainPlan.toMap();
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=trainPlanServer.getTrainPlansCount(map);
			List<TrainPlan> trainPlans=trainPlanServer.getTrainPlans(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", trainPlans);
			return result;
		}
	}
	//http://localhost:8080/pams/trainingPlan/add_trainPlan?trainPlan.unitId=2&trainPlan.deptId=3&trainPlan.startTime=2018-02-23 17:46:24&trainPlan.endTime=2018-02-27 17:46:24&trainPlan.trainContent=get请求添加测试
	@RequestMapping(value="/add_trainPlan",method=RequestMethod.POST)
	@ResponseBody
	public String addTrainPlan(@BeanParam TrainPlan trainPlan){
		JSONObject json = new JSONObject();
		int status=-1;
		status=trainPlanServer.addTrainPlan(trainPlan);
		json.put("status", status);
		return json.toString();
	}
	
	@RequestMapping(value="/update_trainPlan",method=RequestMethod.POST)
	@ResponseBody
	public String updateTrainPlan(@BeanParam TrainPlan trainPlan){
		JSONObject json = new JSONObject();
		int status=-1;
		status=trainPlanServer.updateTrainPlan(trainPlan);
		json.put("status", status);
		return json.toString();
	}
	
	@RequestMapping(value="/delete_trainPlan",method=RequestMethod.POST)
	@ResponseBody
	public String deleteTrainPlan(@BeanParam TrainPlan trainPlan){
		JSONObject json = new JSONObject();
		int status=-1;
		status=trainPlanServer.deleteTrainPlan(trainPlan);
		json.put("status", status);
		return json.toString();
	}
	
	//用户界面请求路径
	@RequestMapping(value="/userTrainPlan_index",method=RequestMethod.GET)
	public String loadUserTrainPlanIndex(){
		return "pms/trainPlanManage/userTrainPlan_index.jsp";
	}
	//用户界面数据载入
	@RequestMapping(value="/load_userTrainPlans",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadUserTrainPlans(@BeanParam TrainPlan trainPlan , Integer page , Integer limit){
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
			MyUser myUser=systemUserServer.getUserByUserName(UserUtil.getUserName());
			trainPlan.setUnitId(myUser.getUnitId());
			trainPlan.setDeptId(myUser.getDeptId());
			Map<String, Object> map=trainPlan.toMap();
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=trainPlanServer.getTrainPlansCount(map);
			List<TrainPlan> trainPlans=trainPlanServer.getTrainPlans(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", trainPlans);
			return result;
		}
	}
}
