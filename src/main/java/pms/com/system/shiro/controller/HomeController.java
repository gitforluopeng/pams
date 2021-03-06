package pms.com.system.shiro.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.system.shiro.services.MenuServiceInter;
import pms.com.system.shiro.vo.MenuVo;

@Controller(value="shiroHome")
@RequestMapping("/backstage")
public class HomeController {
	
	@Resource
	private MenuServiceInter menuService;
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String homeView(){
		
		return "backstage/home.jsp";
	}
	
	@RequestMapping(value="/load_menus", method=RequestMethod.GET)
	@ResponseBody
	public Map<Integer, MenuVo> loadAllMenus(){
		return menuService.loadUserMenuForUserName();
	}
	
}
