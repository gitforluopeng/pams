package pms.com.system.shiro.services;

import java.util.Set;

import pms.com.system.shiro.model.ShiroRole;

public interface ShiroRoleServiceInter {
	
	public Set<ShiroRole> loadAllShiroRoles();
	
	/**
	 * Task : 通过用户主键查询用户拥有的权限
	 * @param id 用户主键
	 * @return 
	 * date :2017年12月5日
	 * @author libo
	 */
	public Set<ShiroRole> loadUserRoles(Long id);
	
}
