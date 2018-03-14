package pms.com.domain.attendanceManage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.attendanceManage.model.Attendance;
import pms.com.domain.attendanceManage.services.AttendanceServiceInter;
import pms.com.domain.systemManagement.services.SystemUserServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.util.UserUtil;
import pms.com.utils.PageUtil;

@Controller
public class AttendanceController {
	@Resource
	private SystemUserServiceInter systemUserServer;
	@Resource
	private AttendanceServiceInter attendanceServer;
	
	//管理员页面映射首页   数据载入还是请求用户的接口
	@RequestMapping(value="/attendance_index",method=RequestMethod.GET)
	public String loadMyUserIndex(){
		return "pms/attendanceManage/attendance_index.jsp";
	}
	
	//显示用户考勤信息页面映射
	@RequestMapping(value="/show_attendance",method=RequestMethod.GET)
	public String showAttendance(){
		return "pms/attendanceManage/show_attendance.jsp";
	}
	
	//开始考勤页面映射
	@RequestMapping(value="/begin_attendance",method=RequestMethod.GET)
	public String beginAttendance(){
		return "pms/attendanceManage/begin_attendance.jsp";
	}
	//管理员系统请求考勤信息接口
	@RequestMapping(value="/load_attendances",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadAttendances(@BeanParam Attendance attendance,Integer page , Integer limit){
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
			Map<String, Object> map=attendance.toMap();
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=attendanceServer.getAttendancesCount(map);
			List<Attendance> attendances=attendanceServer.getAttendances(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", attendances);
			return result;
		}
	}
	//发布考勤请求接口
	//http://localhost:8080/pams/attendanceManage/add_attendance?attendance.userId=1&attendance.userName=libo&attendance.personName=李波&attendance.unitId=2&attendance.unitName=成都法院&attendance.deptId=3&attendance.deptName=法务部&attendance.attendanceTime=2018-02-26&attendance.attendanceSituation=1
	@RequestMapping(value="/add_attendance",method=RequestMethod.GET)
	@ResponseBody
	public String addInfo(@BeanParam Attendance attendance){
		JSONObject json = new JSONObject();
		int status=-1;
		status=attendanceServer.addAttendance(attendance);
		json.put("status", status);
		return json.toString();
	}
	
	//普通用户系统初始化页面映射
	@RequestMapping(value="/myattendances_index",method=RequestMethod.GET)
	public String loadMyAttendanceInfoIndex(){
		return "pms/attendanceManage/myattendances_index.jsp";
	}
	
	//普通用户系统初始化数据映射  除了默认的page和limit参数，不需要带其他参数----只看我的
	@RequestMapping(value="/load_myAttendances",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadMyAttendances(@BeanParam Attendance attendance,Integer page , Integer limit){
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
			Map<String, Object> map=attendance.toMap();
			map.put("userId", UserUtil.getShiroUser().getId());
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=attendanceServer.getAttendancesCount(map);
			List<Attendance> attendances=attendanceServer.getAttendances(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", attendances);
			return result;
		}
	}
}
