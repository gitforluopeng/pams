package pms.com.domain.analyze.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.analyze.services.EduCountServiceInter;

@Controller
public class AnalyzeController {
	@Resource
	private EduCountServiceInter eduCountServer;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/*
	 * 统计员工学历
	 */
	@RequestMapping(value="/load_eduCountEcharData",method=RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getEduCountEchar(){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result=eduCountServer.getEduCountEcharData();
		return result;
	}
	
	/*
	 * 统计员工考勤
	 * result.put("value", allPersonCount);1:已到岗 2:未到岗 3:已请假 4:迟到 5:早退
	 * result.put("name", allAttName);
	 */
	@RequestMapping(value="/load_attCountEcharData",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAttCountEchar(String queStartTime,String queEndTime) throws ParseException{
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Date startTime=null;
		Date endTime=null;
		if (!StringUtils.isEmpty(queStartTime)) {
			startTime=dateFormat.parse(queStartTime);
		}
		if (!StringUtils.isEmpty(queEndTime)) {
			endTime=dateFormat.parse(queEndTime);
		}
		map.put("queStartTime", startTime);
		map.put("queEndTime", endTime);
		result=eduCountServer.getAttCountEcharData(map);
		return result;
	}
}
