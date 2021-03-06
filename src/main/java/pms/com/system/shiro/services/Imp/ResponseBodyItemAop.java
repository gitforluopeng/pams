package pms.com.system.shiro.services.Imp;

import java.util.Collection;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import pms.com.system.shiro.exception.ResponseBodyItemException;

@Aspect
@Component
public class ResponseBodyItemAop {
	
	@Pointcut(value="@annotation(pms.com.system.shiro.annotate.ResponseBodyItem)")
	public void dealItem(){}
	
	@AfterReturning(pointcut="dealItem()", returning="obj")
	public String dealImp(Object obj){
		JSONArray jsonArray = new JSONArray();
		if(obj instanceof Collection || obj instanceof Map){
			jsonArray.put(obj);
		}else {
			throw new ResponseBodyItemException();
		}
		return jsonArray.toString();
	}
	
}
