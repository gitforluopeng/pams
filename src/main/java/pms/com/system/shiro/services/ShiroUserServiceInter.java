package pms.com.system.shiro.services;

import java.util.List;
import java.util.Set;

import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroRole;
import pms.com.system.shiro.model.ShiroUser;

public interface ShiroUserServiceInter {
	
	/**
	 * Task : 通过用户登陆账户获取用户
	 * @param username 用户登陆用的账户
	 * @return 如果获取成功返回shiroUser对象，反之返回null
	 * date :2017年10月8日
	 * @author libo
	 */
	public ShiroUser loadForUsername(String username);
	
	/**
	 * Task : 通过用户组id 获取用户的所有权限 
	 * @param shiroGroupId 用户组id
	 * @return 如果有则返回 shiroRole set集合，反之为null
	 * date :2017年10月8日
	 * @author libo
	 */
	public Set<ShiroRole> loadUserAllRolesForShiroGroupId(Long shiroGroupId);
	
	/**
	 * Task : 通过用户拥有的权限id，获取所拥有的资源
	 * @param shiroRoleIds 用户所拥有的权限的id list集合
	 * @return 如果有则返回shiroResources list集合，反之为null
	 * date :2017年10月8日
	 * @author libo
	 */
	public List<ShiroResources> loadUserAllResourecsForShiroRoleIds(List<Long> shiroRoleIds);
	
	/**
	 * Task : 通过用户拥有的权限获取用户拥有的菜单
	 * @param shiroRoleIds 用户拥有的权限id集合
	 * @return 如果有返回集合，反之null
	 * date :2017年10月14日
	 * @author libo
	 */
	public List<ShiroResources> loadUserAllMenuResources(List<Long> shiroRoleIds);
	
}
