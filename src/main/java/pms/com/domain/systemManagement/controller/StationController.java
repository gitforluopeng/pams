package pms.com.domain.systemManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.systemManagement.model.Station;
import pms.com.domain.systemManagement.services.StationServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.utils.PageUtil;

@Controller
public class StationController {
	@Resource
	private StationServiceInter stationServer;
	
	@RequestMapping(value="/station_index",method=RequestMethod.GET)
	public String loadStationIndex(){
		return "pms/stationManage/station_index.jsp";
	}
	
	@RequestMapping(value="/load_stations",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadStations(@BeanParam Station station,Integer page , Integer limit){
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
			Map<String, Object> map=station.toMap();
			map.put("startNum", PageUtil.getStartNum(page, limit));
			map.put("pageSize", limit);
			int count=stationServer.getStationCount(map);
			List<Station> stations=stationServer.getStation(map);
			result.put("code", 0);
			result.put("msg", "请求数据成功");
			result.put("count", count);
			result.put("data", stations);
			return result;
		}
	}
	
	@RequestMapping(value="/add_station",method=RequestMethod.POST)
	@ResponseBody
	public String addStation(@BeanParam Station station){
		JSONObject json = new JSONObject();
		int status=-1;
		status=stationServer.addStation(station);
		json.put("status", status);
		return json.toString();
	}
	
	@RequestMapping(value="/update_station",method=RequestMethod.POST)
	@ResponseBody
	public String updateStation(@BeanParam Station station,String unEditName){
		JSONObject json = new JSONObject();
		int status=-1;
		status=stationServer.updateStation(station,unEditName);
		json.put("status", status);
		return json.toString();
	}
	
	@RequestMapping(value="/delete_station",method=RequestMethod.POST)
	@ResponseBody
	public String deleteStation(@BeanParam Station station){
		JSONObject json = new JSONObject();
		int status=-1;
		status=stationServer.deleteStation(station);
		json.put("status", status);
		return json.toString();
	}
}
