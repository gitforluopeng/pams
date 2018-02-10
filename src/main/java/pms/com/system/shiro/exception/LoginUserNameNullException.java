package pms.com.system.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class LoginUserNameNullException extends AuthenticationException {

	/**
	 * Task : 
	 * date :2017年10月8日
	 * @author libo
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		
		return super.getMessage()+": 未找到此用户";
	}

}
