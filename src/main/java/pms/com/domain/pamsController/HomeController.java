package pms.com.domain.pamsController;


import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import lb.soft.com.objectSize.ObjectUtil;
import pms.com.domain.systemManagement.model.MyUser;
import pms.com.domain.systemManagement.model.SystemName;
import pms.com.domain.systemManagement.services.SystemNameServiceInter;
import pms.com.domain.systemManagement.services.SystemUserServiceInter;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.services.MenuServiceInter;
import pms.com.system.shiro.services.ShiroCacheServiceInter;
import pms.com.system.shiro.util.UserUtil;
import pms.com.system.shiro.vo.MenuVo;
import pms.com.utils.LoggerUtil;

@Controller
public class HomeController {
	
	@Autowired
	private MenuServiceInter menuService;
	@Autowired
	private SecurityManager securityManager;
	@Autowired
	private ShiroCacheServiceInter shiroCacheService;
	@Autowired
	private SystemUserServiceInter systemUserService;
	@Autowired
	private SystemNameServiceInter systemNameService;
	
	@RequestMapping("/home")
	public ModelAndView homeView(ModelAndView modelAndView){
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		MyUser myUser = systemUserService.getUserByShiroUserId(user.getId());
		String role = null;
		if(SecurityUtils.getSubject().hasRole("admin")){
			role = "admin";
		}else if(SecurityUtils.getSubject().hasRole("user")){
			role = "user";
		}
		modelAndView.addObject("myUser", myUser);
		modelAndView.addObject("role",role);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("pms/home.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value="/load_menus", method=RequestMethod.GET)
	@ResponseBody
	public Map<Integer, MenuVo> loadUserMenus(){
		
		return menuService.loadUserMenuForUserName();
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logOut(){
		shiroCacheService.clearUserCache(UserUtil.getShiroUser());
		securityManager.logout(SecurityUtils.getSubject());
		return "redirect:/login_view";
	}
	
	@RequestMapping(value="/clear_cache",method=RequestMethod.GET)
	@ResponseBody
	public String testClearCache(){
		shiroCacheService.clearSystemCache();
		return "ok";
	}
	
	@RequestMapping("/main_view")
	public String gsHomeView(){
		LoggerUtil.consleLogger.debug("ssssssssssssssssssssssssssssssssssssssssssssssss");
		LoggerUtil.consleLogger.debug(ObjectUtil.sizeOf(this));
		return "pms/pmsHome/index.jsp";
	}
}
