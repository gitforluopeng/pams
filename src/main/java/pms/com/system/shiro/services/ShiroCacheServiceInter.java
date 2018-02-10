package pms.com.system.shiro.services;

import pms.com.system.shiro.model.ShiroUser;

public interface ShiroCacheServiceInter {
	
	/**
	 * Task : 清除用户的缓存,shiroUserCache, userRoleCache中的数据
	 * @param shiroUser 用户对象
	 * date :2017年10月8日
	 * @author libo
	 */
	public void clearUserCache(ShiroUser shiroUser);
	
	/**
	 * Task : 清除系统缓存
	 * date :2017年12月6日
	 * @author libo
	 */
	public void clearSystemCache();
	
}
