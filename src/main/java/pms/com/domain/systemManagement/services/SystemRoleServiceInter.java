package pms.com.domain.systemManagement.services;

import java.util.List;

import pms.com.system.shiro.model.ShiroResources;
import pms.com.system.shiro.model.ShiroResourcesRoles;
import pms.com.system.shiro.model.ShiroRole;

public interface SystemRoleServiceInter {
	/**
	 * 
	 * Task : 分页查询系统角色
	 * @param pageIndex 当前页
	 * @param pageSize 分页大小
	 * @return List<ShiroRole>类型的集合
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public List<ShiroRole> getAllRoleForPage(int pageIndex, int pageSize);
	/**
	 * 
	 * Task : 查询角色总数
	 * @return 角色总数
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int getAllRoleCount();
	/**
	 * 
	 * Task : 添加角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 成功返回1，已有返回0，失败返回-1
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int addRole(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 删除角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 成功返回1，失败返回-1，不能删返回0
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int deleteRole(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 编辑角色
	 * @param shiroRole ShiroRole类型的实体对象
	 * @param unEditName 未编辑之前的名字
	 * @return 成功返回1，失败返回-1，已有该角色返回0
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int updateRole(ShiroRole shiroRole,String unEditName);
	/**
	 * 
	 * Task : 更改角色是否启用
	 * @param shiroRole ShiroRole类型的实体对象
	 * @return 失败返回-1，成功返回1
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int updateRoleIsUse(ShiroRole shiroRole);
	/**
	 * 
	 * Task : 加载所有系统资源
	 * @return List<ShiroResources>类型的集合对象
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public List<ShiroResources> loadAllShiroResources();
	/**
	 * 
	 * Task : 通过shiroroleid加载系统角色
	 * @param shiroRoleIds shiroroleid集合
	 * @return List<ShiroResources>类型的实体对象集合
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public List<ShiroResources> loadAllByShiroRoleId(Long shiroRoleId);
	/**
	 * 
	 * Task : 为角色添加资源
	 * @param shiroResourcesRoles ShiroResourcesRoles类型的实体对象
	 * @return 添加成功返回1，失败返回-1，已有该资源返回0
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int addResouceRole(ShiroResourcesRoles shiroResourcesRoles);
	/**
	 * 
	 * Task : 删除角色的资源
	 * @param shiroResourcesRoles ShiroResourcesRoles类型的实体对象
	 * @return 删除成功返回1，删除失败返回-1
	 * date :2017年12月8日
	 * @author luopeng
	 */
	public int deleteResouceRole(ShiroResourcesRoles shiroResourcesRoles);
}
