package pms.com.system.shiro.services.Imp;

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pms.com.utils.LoggerUtil;



public class ControllerMappingHandlerImp extends RequestMappingHandlerMapping {
	
	private String project_method_path;
	
	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		
		if(StringUtils.isEmpty(project_method_path)){
			project_method_path = "";
		}
		
		String modelName = getProjectModelName(handlerType);
		
		LoggerUtil.consleLogger.info("[ControllerMappingHandlerImp ----> detectHandlerMethods]: handler class name "+handlerType.getName());
		RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
		if(info == null) return info;
		PatternsRequestCondition patterns = info.getPatternsCondition();
		
		Set<String> pathSet = patterns.getPatterns();
		String[] pattens = new String[pathSet.size()];
		int index = 0;
		for(String path: pathSet){
			if(!modelName.equals(""))
				path = "/"+modelName+path;
			pattens[index++] = path;
		}

		
		RequestMappingInfo info2 = new RequestMappingInfo(new PatternsRequestCondition(pattens, getUrlPathHelper()
					,getPathMatcher(), this.useSuffixPatternMatch(), this.useTrailingSlashMatch(),this.getFileExtensions())
				, info.getMethodsCondition()
				, info.getParamsCondition()
				, info.getHeadersCondition()
				, info.getConsumesCondition()
				, info.getProducesCondition()
				, info.getCustomCondition());
		LoggerUtil.consleLogger.info("[ControllerMappingHandlerImp ----> detectHandlerMethods]: handler class name "+info2.getPatternsCondition().getPatterns());
		return info2;
	}
	
	public String getProjectModelName(Class<?> handlerType){
		
		String projectModelName = "";
		String packgeName = handlerType.getName();
		if(project_method_path.equals("") || !packgeName.contains(project_method_path) 
				|| !packgeName.contains("controller")) return "";
		packgeName = packgeName.replace(project_method_path+".", "");
		projectModelName = packgeName.substring(0,packgeName.indexOf('.'));
		if(projectModelName.equals("controller")){
			projectModelName = "";
		}
		
		return projectModelName;
	}

	public String getProject_method_path() {
		return project_method_path;
	}

	public void setProject_method_path(String project_method_path) {
		this.project_method_path = project_method_path;
	}
	
}
