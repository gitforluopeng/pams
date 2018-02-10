package pms.com.system.shiro.model;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordTokenUser extends UsernamePasswordToken{

	/**
	 * Task : 
	 * date :2017年10月13日
	 * @author libo
	 */
	private static final long serialVersionUID = -1061122536212410647L;
	
	private String token;
	
	public UsernamePasswordTokenUser(String username, String password, String token) {
		super(username, password);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
