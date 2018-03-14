package pms.com.domain.salaryManage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pms.com.domain.salaryManage.model.Salary;
import pms.com.domain.salaryManage.services.SalaryServiceInter;
import pms.com.domain.systemManagement.services.SystemUserServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.PageUtil;

@Controller
public class SalaryController {
	@Resource
	private SystemUserServiceInter systemUserServer;
	@Resource
	private SalaryServiceInter salaryServer;

	//管理员页面映射首页   数据载入还是请求用户的接口
	@RequestMapping(value="/salary_index",method=RequestMethod.GET)
	public String loadMyUserIndex(){
		return "pms/salaryManage/salary_index.jsp";
	}
	//显示用户工资信息页面映射
	@RequestMapping(value="/show_salary",method=RequestMethod.GET)
	public String showSalary(){
		return "pms/salaryManage/show_salary.jsp";
	}

	//发放工资页面映射
	@RequestMapping(value="/publish_salary",method=RequestMethod.GET)
	public String publishSalary(){
		return "pms/salaryManage/publish_salary.jsp";
	}

	//编辑工资页面映射
	@RequestMapping(value="/salary_edit",method=RequestMethod.GET)
	public String salaryEdit(){
		return "pms/salaryManage/salary_edit.jsp";
	}

	//管理员系统请求工资信息接口
	@RequestMapping(value="/load_salaries",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadSalaries(@BeanParam Salary salary,Integer page , Integer limit){
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
			Map<String, Object> map=salary.toMap();
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=salaryServer.getSalarysCount(map);
			List<Salary> salaries=salaryServer.getSalarys(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", salaries);
			return result;
		}
	}

	//发放工资请求接口
	//http://localhost:8080/pams/salaryManage/add_salary?salary.userId=1&salary.userName=libo&salary.personName=李波&salary.unitId=2&salary.unitName=成都法院&salary.deptId=3&salary.deptName=法务部&salary.shouldPay=5000&salary.realPay=4000&salary.deductions=个人所得税&salary.payTime=2018-02
	@RequestMapping(value="/add_salary",method=RequestMethod.GET)
	@ResponseBody
	public String addSalary(@BeanParam Salary salary){
		JSONObject json = new JSONObject();
		int status=-1;
		status=salaryServer.addSalary(salary);
		json.put("status", status);
		return json.toString();
	}
	//编辑工资请求接口
	@RequestMapping(value="/update_salary",method=RequestMethod.GET)
	@ResponseBody
	public String updateSalary(@BeanParam Salary salary){
		JSONObject json = new JSONObject();
		int status=-1;
		status=salaryServer.updateSalary(salary);
		json.put("status", status);
		return json.toString();
	}
	//删除工资请求接口
	@RequestMapping(value="/delete_salary",method=RequestMethod.GET)
	@ResponseBody
	public String deleteSalary(@BeanParam Salary salary){
		JSONObject json = new JSONObject();
		int status=-1;
		status=salaryServer.deleteSalary(salary);
		json.put("status", status);
		return json.toString();
	}

	//普通用户系统初始化页面映射
	@RequestMapping(value="/mySalary_index",method=RequestMethod.GET)
	public String loadMySalaryInfoIndex(){
		return "pms/salaryManage/mySalary_index.jsp";
	}
	//普通用户系统初始化数据映射  除了默认的page和limit参数，不需要带其他参数
	@RequestMapping(value="/load_mySalaries",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadMySalaries(@BeanParam Salary salary,Integer page , Integer limit){
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
			Map<String, Object> map=salary.toMap();
			map.put("userId", UserUtil.getShiroUser().getId());
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=salaryServer.getSalarysCount(map);
			List<Salary> salaries=salaryServer.getSalarys(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", salaries);
			return result;
		}
	}
}
