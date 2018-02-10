package pms.com.system.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class LoginLimitException extends AuthenticationException {

	/**
	 * Task : 
	 * date :2017年10月6日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		
		return super.getMessage()+": 登陆次数超过限制请一小时后再尝试登陆";
	}
	
	
	
}
