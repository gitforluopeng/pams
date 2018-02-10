package pms.com.system.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import pms.com.system.shiro.model.ShiroUser;
import pms.com.utils.Md5;

public class UserUtil {
	
	/**
	 * Task : 获取用户名
	 * @return 如果有则返回用户登陆名,反之为null
	 * date :2017年10月14日
	 * @author libo
	 */
	public static String getUserName(){
		Subject subject = SecurityUtils.getSubject();
		if(subject == null || subject.getPrincipal() == null){
			return null;
		}
		return ((ShiroUser)subject.getPrincipal()).getUsername();
	}
	
	/**
	 * Task : 获取用户的用户对象
	 * @return 如果登陆了返回shiroUser对象，反之为null
	 * date :2017年10月14日
	 * @author libo
	 */
	public static ShiroUser getShiroUser(){
		Subject subject = SecurityUtils.getSubject();
		if(subject == null || subject.getPrincipal() == null){
			return null;
		}
		return (ShiroUser)subject.getPrincipal();
	}
	
	/**
	 * Task : 加密用户密码
	 * @param password 用户明文密码
	 * @param salt 盐
	 * @return 密码
	 * date :2017年12月1日
	 * @author libo
	 */
	public static String encipherPassword(String password, String salt){
		String pwd = Md5.getCode32(password+Md5.getCode32(salt));
		return pwd;
	}
	
	/**
	 * Task : 判断用户是否包含权限
	 * @param roleName 权限名称
	 * @return
	 * date :2017年12月13日
	 */
	public static boolean checkUserHasRole(String roleName){
		return SecurityUtils.getSubject().hasRole(roleName);
	}
	
}
