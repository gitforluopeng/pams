package pms.com.domain.systemManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.systemManagement.model.OperationLog;
import pms.com.domain.systemManagement.services.OperationLogInter;

@Controller
public class OperationLogController {
	private static String basePath = "pms/systemLog";
	
	@Resource
	private OperationLogInter operationLogInter;
	
	@RequestMapping(value="/operation_index",method=RequestMethod.GET)
	public String operationIndex(){
		return basePath+"/system_log.jsp";
	}
	
	@RequestMapping(value="/load_operation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadAllOperationForPage(Integer page, Integer limit){
		Map<String , Object> result = new HashMap<String, Object>();
		List<OperationLog> operationLogs = operationLogInter.getAlLogsForPage(page-1, limit);
		int count = operationLogInter.getLogsCount();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", operationLogs);
		return result;
	}
	
	@RequestMapping(value="/query_operation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadQuery(String username,String date,Integer page, Integer limit){
		Map<String, Object> result = new HashMap<String, Object>();
		List<OperationLog> operationLogs = operationLogInter.queryOperation(username,date,page-1,limit);
		int count = operationLogInter.queryCount(username,date);
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", operationLogs);
		return result;
	}
}
