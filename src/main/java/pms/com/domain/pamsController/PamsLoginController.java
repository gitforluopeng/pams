package pms.com.domain.pamsController;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.system.shiro.exception.LoginLimitException;
import pms.com.system.shiro.exception.LoginUserIsLockException;
import pms.com.system.shiro.exception.LoginUserNotFountException;
import pms.com.system.shiro.model.ShiroUser;
import pms.com.system.shiro.services.ShiroUserServiceInter;

@Controller
public class PamsLoginController {
	
	@Autowired
	private SecurityManager securityManager;
	
	@Autowired
	private ShiroUserServiceInter shiroUserService;
	
	@RequestMapping(value="/login_view", method=RequestMethod.GET)
	public String loginView(){
		return "pms/login.jsp";
	}
	
	@RequestMapping(value="/loging",method=RequestMethod.POST)
	@ResponseBody
	public String login(String username, String password, boolean step){
		
		JSONObject result = new JSONObject();
		if(step){
			ShiroUser shiroUser = shiroUserService.loadForUsername(username);
			result.put("status", "success");
			if(shiroUser != null) {
				result.put("token", shiroUser.salt);
			}else {
				result.put("status", "userNotFind");
			}
			return result.toString();
		}
	
		result.put("loginStatus", true);
		result.put("loginSuccessUrl", "/pams/home");
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken user = new UsernamePasswordToken(username, password);
		
		try {
			subject.login(user);
		} catch (AuthenticationException e) {
			result.put("loginStatus", false);
			String errorInfo = null;
			if(e instanceof LoginLimitException){
				errorInfo = "loginLimit";
			}else if(e instanceof LoginUserIsLockException){
				errorInfo = "userIsLock";
			}else if(e instanceof LoginUserNotFountException){
				errorInfo = "notFountUser";
			}
			result.put("error", errorInfo);
		}
		
		return result.toString();
	}
	
}
