package pms.com.system.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/backstage")
public class ResourcesController {
	
	private static String baseViewPath = "backstage/resources/";
	
	@RequestMapping(value="/resources/home", method=RequestMethod.GET)
	public String mainView(){
		
		return baseViewPath+"home.jsp";
	}

}
